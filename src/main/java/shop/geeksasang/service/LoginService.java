package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import shop.geeksasang.config.domain.Status;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.BaseResponseStatus;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.University;
import shop.geeksasang.dto.login.LoginReq;
import shop.geeksasang.dto.login.LoginRes;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.repository.MemberRepository;
import shop.geeksasang.utils.encrypt.SHA256;
import shop.geeksasang.utils.jwt.JwtService;

import java.util.LinkedHashMap;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    public LoginRes login(LoginReq dto){

        Member member = memberRepository.findMemberByLoginId(dto.getLoginId()).orElseThrow(RuntimeException::new);

        String password = SHA256.encrypt(dto.getPassword());

        //password
        if(!password.equals(member.getPassword())){
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_PASSWORD);
        }
        //status
        if(member.getStatus().equals(Status.INACTIVE)){
            throw new BaseException(BaseResponseStatus.INACTIVE_STATUS);
        }

        JwtInfo vo = JwtInfo.builder()
                .userId(member.getId())
                .universityId(1) //차후에 수정할
                .build();

        String jwt = jwtService.createJwt(vo);

        return LoginRes.builder().jwt(jwt).build();
    }

    @GetMapping
    public LinkedHashMap test(){
        return jwtService.getJwtInfo();
    }

}
