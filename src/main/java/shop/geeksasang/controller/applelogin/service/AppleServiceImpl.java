package shop.geeksasang.controller.applelogin.service;


import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
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
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.config.type.MemberLoginType;
import shop.geeksasang.controller.applelogin.model.Account;
import shop.geeksasang.controller.applelogin.model.DeleteUserReq;
import shop.geeksasang.controller.applelogin.model.ServicesResponse;
import shop.geeksasang.controller.applelogin.model.TokenResponse;
import shop.geeksasang.controller.applelogin.util.AppleUtils;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.dto.member.post.CreateUserAppleReq;
import shop.geeksasang.repository.member.MemberRepository;
import shop.geeksasang.service.member.MemberService;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppleServiceImpl {

    private final AppleUtils appleUtils;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Value("${APPLE.AUD}")
    String client_id;

    /**
     * 유효한 id_token인 경우 client_secret 생성
     */
    public String getAppleClientSecret(String id_token) throws NoSuchAlgorithmException {

        if (appleUtils.verifyIdentityToken(id_token)) {
            return appleUtils.createClientSecret();
        }

        return null;
    }

    /**
     * code 또는 refresh_token가 유효한지 Apple Server에 검증 요청
     */
    @Transactional
    public TokenResponse requestCodeValidations(ServicesResponse serviceResponse, String refresh_token) throws NoSuchAlgorithmException {

        TokenResponse tokenResponse = new TokenResponse();

        String code = serviceResponse.getCode();
        String client_secret = getAppleClientSecret(serviceResponse.getId_token());

        JSONObject user = new JSONObject(serviceResponse.getUser());
        //User saveduser = null;
        Member saveduser = null;

        // 이메일 추출
        String email = user.getAsString("email");

        // 이름 추출
        Map<String, String> name = (Map<String, String>) user.get("name");
        String lastName = name.get("lastName");
        String firstName = name.get("firstName");
        String fullName = lastName + firstName;

        // 만약 처음 인증하는 유저여서  refresh 토큰 없으면 client_secret, authorization_code로 검증
        if (client_secret != null && code != null && refresh_token == null) {
            tokenResponse = appleUtils.validateAuthorizationGrantCode(client_secret, code);

            // 유저 생성
            CreateUserAppleReq createUserAppleReq = new CreateUserAppleReq(email, tokenResponse.getRefresh_token(),fullName, MemberLoginType.APPLE_USER);
            //TODO:saveduser = userService.createUserApple(createUserAppleReq);
            saveduser = memberService.createUserApple(createUserAppleReq);
        }
        // 이미 refresh 토큰 있는 유저면 client_secret, refresh_token로 검증
        else if (client_secret != null && code == null && refresh_token != null) {
            tokenResponse = appleUtils.validateAnExistingRefreshToken(client_secret, refresh_token);
        }

        //TODO:tokenResponse.setAccount(new Account(serviceResponse.getState(), code, tokenResponse.getId_token(), user, serviceResponse.getIdentifier(), serviceResponse.getHasRequirementInfo()));
        //TODO:tokenResponse.setUserType(MemberLoginType.APPLE_USER.toString());
        //TODO:tokenResponse.setUserStatus(UserStatus.LOGIN);

        // userId 설정
        if(refresh_token == null){
            tokenResponse.setUserId(saveduser.getId());
            //TODO:saveduser.changeUserStatus(UserStatus.LOGIN);
        }
        else{
            //TODO:User findUser = userRepository.findByAppleRefreshToken(refresh_token)
                    //.orElseThrow(() -> new NotFoundUserException());
            Member findUser = memberRepository.findByAppleRefreshToken(refresh_token)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_USER));
            tokenResponse.setUserId(findUser.getId());
            //TODO:tokenResponse.setUserName(findUser.getName());
            //TODO:findUser.changeUserStatus(UserStatus.LOGIN);
        }

        return tokenResponse;
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
        Member user = memberRepository.findMemberByIdAndStatus(deleteUserReq.getUserId()).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_USER));
        //TODO:user.deleteUser(); // 유저 개인정보 null 만드는 메서드

    }
}
