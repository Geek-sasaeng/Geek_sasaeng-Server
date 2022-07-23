package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import shop.geeksasang.config.status.LoginStatus;
import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.login.*;
import shop.geeksasang.repository.MemberRepository;
import shop.geeksasang.utils.encrypt.SHA256;
import shop.geeksasang.utils.jwt.JwtService;

import java.util.LinkedHashMap;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.NOT_EXISTS_LOGINID;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final MemberRepository memberRepository;
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

        // 로그인 횟수 상태 (loginStatus) Never -> NotNever변경
        if(loginStatus.equals(LoginStatus.NEVER)){
            member.changeLoginStatusToNotNever();
        }


        JwtInfo vo = JwtInfo.builder()
                .userId(member.getId())
                .universityId(member.getUniversity().getId())
                .build();

        String jwt = jwtService.createJwt(vo);

        return PostLoginRes.builder()
                .jwt(jwt)
                .loginStatus(loginStatus)
                .build();
    }
    public PostSocialLoginRes socialLogin(PostSocialLoginReq dto){

    }

    @GetMapping
    public LinkedHashMap test(){
        return jwtService.getJwtInfo();
    }

}
