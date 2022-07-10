package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import shop.geeksasang.config.domain.LoginStatus;
import shop.geeksasang.config.domain.Status;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.BaseResponseStatus;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.University;
import shop.geeksasang.dto.login.LoginReq;
import shop.geeksasang.dto.login.LoginRes;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.repository.MemberRepository;
import shop.geeksasang.utils.encrypt.SHA256;
import shop.geeksasang.utils.jwt.JwtService;

import java.util.LinkedHashMap;

import static shop.geeksasang.config.exception.BaseResponseStatus.NOT_EXISTS_LOGINID;
import static shop.geeksasang.config.exception.BaseResponseStatus.NOT_EXISTS_PARTICIPANT;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    public LoginRes login(LoginReq dto){

        Member member = memberRepository.findMemberByLoginId(dto.getLoginId())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_LOGINID));

        String password = SHA256.encrypt(dto.getPassword());
        LoginStatus loginStatus = member.getLoginStatus(); // 로그인 횟수 상태


        //password
        if(!password.equals(member.getPassword())){
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_PASSWORD);
        }
        //status
        if(member.getStatus().equals(Status.INACTIVE)){
            throw new BaseException(BaseResponseStatus.INACTIVE_STATUS);
        }

        // 로그인 횟수 상태 (loginStatus) Never -> NotNever변경
        if(loginStatus.equals(LoginStatus.NEVER)){
            member.changeLoginStatusToNotNever();
        }


        JwtInfo vo = JwtInfo.builder()
                .userId(member.getId())
                .universityId(member.getUniversity().getId())
                .build();

        String jwt = jwtService.createJwt(vo);

        return LoginRes.builder()
                .jwt(jwt)
                .loginStatus(loginStatus)
                .build();
    }

    @GetMapping
    public LinkedHashMap test(){
        return jwtService.getJwtInfo();
    }

}
