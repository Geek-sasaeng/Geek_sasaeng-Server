package shop.geeksasang.controller.applelogin.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.status.LoginStatus;
import shop.geeksasang.config.type.MemberLoginType;
import shop.geeksasang.controller.applelogin.controller.TempDto;
import shop.geeksasang.controller.applelogin.model.*;
import shop.geeksasang.controller.applelogin.util.AppleUtils;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.login.post.PostLoginRes;
import shop.geeksasang.dto.member.post.CreateUserAppleReq;
import shop.geeksasang.dto.member.post.PostSocialRegisterRes;
import shop.geeksasang.repository.member.MemberRepository;
import shop.geeksasang.service.member.MemberService;
import shop.geeksasang.utils.jwt.JwtService;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;

import static shop.geeksasang.config.TransactionManagerConfig.JPA_TRANSACTION_MANAGER;
import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class AppleServiceImpl {

    private final AppleUtils appleUtils;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Value("${APPLE.AUD}")
    String client_id;

    /**
     * code 또는 refresh_token가 유효한지 Apple Server에 검증 요청
     */
    public TokenResponse signUp(AppleSignUpReq req) throws NoSuchAlgorithmException {

        String client_secret = getAppleClientSecret(req.getIdToken());

        // 만약 처음 인증하는 유저여서  refresh 토큰 없으면 client_secret, authorization_code로 검증
        TokenResponse tokenResponse = appleUtils.validateAuthorizationGrantCode(client_secret, req.getCode());
        TempDto tempDto = memberService.registerAppleMember(req, tokenResponse.getRefresh_token());
        tokenResponse.setJwt(tempDto.getJwt());
        tokenResponse.setNickName(tempDto.getNickName());

        return tokenResponse;
    }



    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public PostLoginRes login(String idToken, String refreshToken, String fcmToken) throws NoSuchAlgorithmException {
        String clientSecret = getAppleClientSecret(idToken);
        TokenResponse tokenResponse = appleUtils.validateAnExistingRefreshToken(clientSecret, refreshToken);
        Member member= memberRepository.findByAppleRefreshToken(refreshToken)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        tokenResponse.setUserId(member.getId());

        JwtInfo vo = JwtInfo.builder()
                .userId(member.getId())
                .universityId(0)
                .build();

        member.updateFcmToken(fcmToken);
        String jwt = jwtService.createJwt(vo);
        if(member.getLoginStatus() == LoginStatus.NEVER){
            return PostLoginRes.builder()
                    .jwt(jwt)
                    .nickName(member.getNickName())
                    .loginStatus(member.getLoginStatus())
                    .dormitoryId(member.getDormitory().getId())
                    .dormitoryName(member.getDormitory().getName())
                    .profileImgUrl(member.getProfileImgUrl())
                    .fcmToken(member.getFcmToken())
                    .memberId(member.getId())
                    .build();
        }
        return PostLoginRes.builder()
                .jwt(jwt)
                .nickName(member.getNickName())
                .loginStatus(member.getLoginStatus())
                .profileImgUrl(member.getProfileImgUrl())
                .fcmToken(member.getFcmToken())
                .memberId(member.getId())
                .build();
    }

    /**
     * Apple login page 호출을 위한 Meta 정보 가져오기
     */
    public Map<String, String> getLoginMetaInfo() {
        return appleUtils.getMetaInfo();
    }

    /**
     * id_token에서 payload 데이터 가져오기
     */
    public String getPayload(String id_token) {
        return appleUtils.decodeFromIdToken(id_token).toString();
    }

    /**
     * 유효한 id_token인 경우 client_secret 생성
     */
    private String getAppleClientSecret(String id_token) throws NoSuchAlgorithmException {

        if (appleUtils.verifyIdentityToken(id_token)) {
            return appleUtils.createClientSecret();
        }

        return null;
    }

//    /**
//     * 리프레시 토큰 검증
//     *
//     * refresh_token은 만료되지 않기 때문에 권한이 필요한 요청일 경우
//     * 굳이 매번 애플 ID 서버로부터 refresh_token을 통해 access_token을 발급 받기보다는
//     * 유저의 refresh_token을 따로 DB나 기타 저장소에 저장해두고 캐싱해두고 조회해서 검증하는편이 성능면에서 낫다는 자료를 참고
//     * https://hwannny.tistory.com/71
//     */
//    @Override
//    public Void validateRefreshToken(Long userId, String refreshToken){
//        User user = userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundUserException());
//
//        if(user.getRefreshToken() == null) throw new NotFoundRefreshTokenException();
//
//        if(!user.getRefreshToken().equals(refreshToken)) throw new InvalidRefreshTokenException();
//
//        return null;
//    }


    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public void deleteUser(DeleteUserReq deleteUserReq) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        String revokeUrl = "https://appleid.apple.com/auth/revoke";

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", client_id);
        params.add("client_secret", appleUtils.createClientSecret());
        params.add("token", deleteUserReq.getRefreshToken());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        restTemplate.postForEntity(revokeUrl, httpEntity, String.class);

        // 유저 정보 삭제 및 유저 상태 변경 (DELETE)
        //TODO:userService.validateRefreshToken(deleteUserReq.getUserId(), deleteUserReq.getRefreshToken());
        memberService.validateRefreshToken(deleteUserReq.getUserId(), deleteUserReq.getRefreshToken());
        //TODO:User user = userRepository.findByUserId(deleteUserReq.getUserId()).orElseThrow(() -> new NotFoundUserException());
        Member user = memberRepository.findMemberByIdAndStatus(deleteUserReq.getUserId()).orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        //TODO:user.deleteUser(); // 유저 개인정보 null 만드는 메서드

    }
}
