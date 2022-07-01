package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import shop.geeksasang.domain.Member;
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
public class LoginService {
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    public LoginRes login(LoginReq dto){
        Member member = memberRepository.findMemberByLoginId(dto.getLoginId()).orElseThrow(RuntimeException::new);

        String password = SHA256.encrypt(dto.getPassword());

        if(!password.equals(member.getPassword())){
            throw new RuntimeException();
        }

        JwtInfo vo = JwtInfo.builder()
                .userId(member.getId())
                .university(null)
                .build();
        String jwt = jwtService.createJwt(vo);
        System.out.println("vo.toString() = " + vo.toString());
        System.out.println("jwt = " + jwt.toString());

        return LoginRes.builder().jwt(jwt).build();
    }

    @GetMapping
    public LinkedHashMap test(){
        return jwtService.getJwtInfo();
    }
}
