package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.status.LoginStatus;
import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.config.type.MemberLoginType;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.login.*;
import shop.geeksasang.dto.login.JwtResponse;
import shop.geeksasang.dto.login.post.PostLoginReq;
import shop.geeksasang.dto.login.post.PostLoginRes;
import shop.geeksasang.dto.login.post.PostSocialLoginReq;
import shop.geeksasang.repository.MemberRepository;
import shop.geeksasang.utils.encrypt.SHA256;
import shop.geeksasang.utils.jwt.JwtService;
import shop.geeksasang.utils.resttemplate.naverlogin.NaverLoginData;
import shop.geeksasang.utils.resttemplate.naverlogin.NaverLoginService;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final MemberRepository memberRepository;
    private final NaverLoginService naverLoginRequest;
    private final JwtService jwtService;

    public PostLoginRes login(PostLoginReq dto){

        Member member = memberRepository.findMemberByLoginId(dto.getLoginId())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_LOGINID));

        String password = SHA256.encrypt(dto.getPassword());

        LoginStatus loginStatus = member.getLoginStatus(); // 로그인 횟수 상태

        //password
        if(!password.equals(member.getPassword())){
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_PASSWORD);
        }

        //status
        if(member.getStatus().equals(BaseStatus.INACTIVE)){
            throw new BaseException(BaseResponseStatus.INACTIVE_STATUS);
        }

        JwtInfo vo = JwtInfo.builder()
                .userId(member.getId())
                .universityId(member.getUniversity().getId())
                .build();

        String jwt = jwtService.createJwt(vo);

        //loginStatus가 NOTNEVER인 경우 dormitoryId,dormitoryName을 추가
        if(loginStatus.equals(LoginStatus.NOTNEVER)){
            return PostLoginRes.builder()
                    .jwt(jwt)
                    .nickName(member.getNickName())
                    .loginStatus(loginStatus)
                    .dormitoryId(member.getDormitory().getId())
                    .dormitoryName(member.getDormitory().getName())
                    .profileImgUrl(member.getProfileImgUrl())
                    .build();
        }
        else{
            return PostLoginRes.builder()
                    .jwt(jwt)
                    .nickName(member.getNickName())
                    .loginStatus(loginStatus)
                    .profileImgUrl(member.getProfileImgUrl())
                    .build();
        }
    }

    // 네이버 로그인
    // 토큰 받아서 DB에 userId 있을 시 로그인, 없을 시 회원가입 화면으로 이동
    public PostLoginRes naverLogin(PostSocialLoginReq dto){
        NaverLoginData data = naverLoginRequest.getToken(dto.getAccessToken());
        String loginId = data.getEmail();


        // DB에 아이디 없을 시 회원가입 화면으로 이동
        Member member = memberRepository.findMemberByLoginId(loginId)
                .orElseThrow(() -> new BaseException(MOVE_NAVER_REGISTER));

        // 네이버 로그인 유저가 아닐 시 예외처리
        if(member.getMemberLoginType() != MemberLoginType.NAVER_USER){
            throw new BaseException(NOT_TYPE_NAVER_USER);
        }

        //status
        if(member.getStatus().equals(BaseStatus.INACTIVE)){
            throw new BaseException(BaseResponseStatus.INACTIVE_STATUS);
        }

        LoginStatus loginStatus = member.getLoginStatus(); // 로그인 횟수 상태

        JwtInfo vo = JwtInfo.builder()
                .userId(member.getId())
                .universityId(member.getUniversity().getId())
                .build();

        String jwt = jwtService.createJwt(vo);

        //loginStatus가 NOTNEVER인 경우 dormitoryId,dormitoryName을 추가
        if(loginStatus.equals(LoginStatus.NOTNEVER)){
            return PostLoginRes.builder()
                    .jwt(jwt)
                    .nickName(member.getNickName())
                    .loginStatus(loginStatus)
                    .profileImgUrl(member.getProfileImgUrl())
                    .dormitoryId(member.getDormitory().getId())
                    .dormitoryName(member.getDormitory().getName())
                    .build();
        }
        else {
            return PostLoginRes.builder()
                    .jwt(jwt)
                    .nickName(member.getNickName())
                    .loginStatus(loginStatus)
                    .profileImgUrl(member.getProfileImgUrl())
                    .build();
        }
    }

    // JWT 유효성 확인
    // 유효성 검증되면 jwtResponse 반환
    public JwtResponse autoLogin(JwtInfo jwtInfo){
        int userIdx = jwtInfo.getUserId();
        Member member = memberRepository.findById(userIdx).orElseThrow(
                () -> new BaseException(NOT_EXIST_USER));
        JwtResponse jwtResponse = getJwtResponse(member);
        return jwtResponse;
    }

    // member로 jwtResponse 가져오기
    public JwtResponse getJwtResponse(Member member){
        return new JwtResponse(member.getLoginId(), member.getNickName(), member.getProfileImgUrl(),
                member.getUniversity().getName(), member.getEmail().getAddress(), member.getPhoneNumber().getNumber(), member.getMemberLoginType(),
                member.getLoginStatus(), member.getDormitory().getId(), member.getDormitory().getName());
    }
}
