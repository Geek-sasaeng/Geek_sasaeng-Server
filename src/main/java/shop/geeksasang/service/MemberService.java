package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.CreateMemberReq;
import shop.geeksasang.repository.MemberRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public Member createMember(CreateMemberReq dto){

        /**
         *
         *  if(dto.getCheckPassword() != dto.getPassword()){
         *             throw new RuntimeException();
         *         }
         */

        //로그인 아이디 중복

        //email 중복

        Member member = dto.toEntity();
        memberRepository.save(member);


        return member;
    }
}
