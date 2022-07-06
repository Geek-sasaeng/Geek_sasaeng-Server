package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.University;

import shop.geeksasang.dto.member.CreateMemberReq;
import shop.geeksasang.dto.member.PatchNicknameReq;
import shop.geeksasang.repository.MemberRepository;
import shop.geeksasang.repository.UniversityRepository;
import shop.geeksasang.utils.jwt.RedisUtil;
import shop.geeksasang.utils.encrypt.SHA256;

import static shop.geeksasang.config.exception.BaseResponseStatus.*;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final UniversityRepository universityRepository;
    private final RedisUtil redisUtil;

    private final long expireTime = 60 * 5L; // 이메일 유효 기간

    @Transactional(readOnly = false)
    public Member createMember(CreateMemberReq dto){

         if(!dto.getCheckPassword().equals(dto.getPassword())) {
             throw new BaseException(DIFFRENT_PASSWORDS);
         }

        if(!memberRepository.findMemberByLoginId(dto.getLoginId()).isEmpty()){
            throw new BaseException(DUPLICATE_USER_LOGIN_ID);
        }

        if(!memberRepository.findMemberByEmail(dto.getEmail()).isEmpty()){
            throw new BaseException(DUPLICATE_USER_EMAIL);
        }

        dto.setPassword(SHA256.encrypt(dto.getPassword()));
        Member member = dto.toEntity();
        University university = universityRepository
                .findUniversitiesByName(dto.getUniversityName())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_UNIVERSITY));

        member.connectUniversity(university);
        member.changeStatusToActive();
        memberRepository.save(member);
        return member;
    }

    @Transactional(readOnly = false)
    public Member UpdateNickname(int id, PatchNicknameReq dto) {
        if(!memberRepository.findMemberByNickName(dto.getNickName()).isEmpty()){
            throw new BaseException(DUPLICATE_USER_NICKNAME);
        }

        Member member = memberRepository.findMemberById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. id="+ id));

        member.updateNickname(dto.getNickName());
        return member;
    }
}
