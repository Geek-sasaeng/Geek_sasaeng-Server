package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.University;
import shop.geeksasang.dto.CreateMemberReq;
import shop.geeksasang.repository.MemberRepository;
import shop.geeksasang.repository.UniversityRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final UniversityRepository universityRepository;

    @Transactional(readOnly = false)
    public Member createMember(CreateMemberReq dto){

         if(!dto.getCheckPassword().equals(dto.getPassword())) {
             throw new RuntimeException();
         }

        //ToDo 로그인 아이디 중복


        //ToDo email 중복


        Member member = dto.toEntity();
        University university = universityRepository
                .findUniversitiesByName(dto.getUniversityName()).orElseThrow(() -> new RuntimeException(""));

        member.connectUniversity(university);
        member.changeStatusToActive();
        memberRepository.save(member);
        return member;
    }
}
