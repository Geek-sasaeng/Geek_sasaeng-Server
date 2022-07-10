package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.University;

import shop.geeksasang.dto.member.*;
import shop.geeksasang.repository.MemberRepository;
import shop.geeksasang.repository.UniversityRepository;
import shop.geeksasang.utils.jwt.RedisUtil;
import shop.geeksasang.utils.encrypt.SHA256;

import java.util.Optional;

import static shop.geeksasang.config.exception.BaseResponseStatus.*;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final UniversityRepository universityRepository;
    private final RedisUtil redisUtil;

    private final long expireTime = 60 * 5L; // 이메일 유효 기간

    // 회원 가입하기
    @Transactional(readOnly = false)
    public Member registerMember(PostRegisterReq dto){
         if(!dto.getCheckPassword().equals(dto.getPassword())) {
             throw new BaseException(DIFFRENT_PASSWORDS);
         }
        if(!memberRepository.findMemberByLoginId(dto.getLoginId()).isEmpty()){
            throw new BaseException(DUPLICATE_USER_LOGIN_ID);
        }
        if(!memberRepository.findMemberByEmail(dto.getEmail()).isEmpty()){
            throw new BaseException(DUPLICATE_USER_EMAIL);
        }

        // 검증: 동의여부가 Y 가 이닌 경우
        if(!dto.getInformationAgreeStatus().equals("Y")){
            throw new BaseException(INVALID_INFORMATIONAGREE_STATUS);
        }
        dto.setPassword(SHA256.encrypt(dto.getPassword()));
        Member member = dto.toEntity();
        University university = universityRepository
                .findUniversityByName(dto.getUniversityName())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_UNIVERSITY));
        member.connectUniversity(university);
        member.changeStatusToActive();
        member.changeLoginStatusToNever(); // 로그인 안해본 상태 디폴트 저장

        memberRepository.save(member);
        return member;
    }

    // 소셜 회원가입 하기
    @Transactional(readOnly = false)
    public Member registerSocialMember(PostSocialRegisterReq dto){
        if(!dto.getCheckPassword().equals(dto.getPassword())) {
            throw new BaseException(DIFFRENT_PASSWORDS);
        }
        if(!memberRepository.findMemberByLoginId(dto.getLoginId()).isEmpty()){
            throw new BaseException(DUPLICATE_USER_LOGIN_ID);
        }
        if(!memberRepository.findMemberByEmail(dto.getEmail()).isEmpty()){
            throw new BaseException(DUPLICATE_USER_EMAIL);
        }

        // 검증: 동의여부가 Y 가 이닌 경우
        if(!dto.getInformationAgreeStatus().equals("Y")){
            throw new BaseException(INVALID_INFORMATIONAGREE_STATUS);
        }
        dto.setPassword(SHA256.encrypt(dto.getPassword()));
        Member member = dto.toEntity();
        University university = universityRepository
                .findUniversityByName(dto.getUniversityName())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_UNIVERSITY));
        member.connectUniversity(university);
        member.changeStatusToActive();
        member.changeLoginStatusToNever(); // 로그인 안해본 상태 디폴트 저장


        memberRepository.save(member);
        return member;
    }
    // 수정: 폰 번호
    @Transactional(readOnly = false) // readOnly = false : 생성, 수정하는 작업에 적용
    public Member updatePhoneNumber(int id, PatchPhoneNumberReq dto){

        // 멤버 아이디로 조회
        Member findMember = memberRepository
                .findById(id)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        // 폰 번호 수정
        findMember.updatePhoneNumber(dto.getPhoneNumber());

        return findMember;
    }

    // 수정: 폰 인증 번호
    @Transactional(readOnly = false)
    public Member updatePhoneValidKey(int id, PatchPhoneValidKeyReq dto){

        //멤버 아이디로 조회
        Member findMember = memberRepository
                .findById(id)
                .orElseThrow(()-> new BaseException(NOT_EXIST_USER));
        //폰 인증번호 수정
        findMember.updatePhoneValidKey(dto.getPhoneValidKey());

        return findMember;
    }

    // 수정: 프로필 이미지
    @Transactional(readOnly = false)
    public Member updateProfileImgUrl(int id, PatchProfileImgUrlReq dto){

        //멤버 아이디로 조회
        Member findMember = memberRepository
                .findById(id)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        //프로필 이미지 수정
        findMember.updateProfileImgUrl(dto.getProfileImgUrl());

        return findMember;
    }

    // 수정: 회원정보 동의 수정
    @Transactional(readOnly = false)
    public Member updateInformationAgreeStatus(int id, PatchInformationAgreeStatusReq dto){

        //멤버 아이디로 조회
        Member findMember = memberRepository
                .findById(id)
                .orElseThrow(()-> new BaseException(NOT_EXIST_USER));
        //동의 여부 수정
        findMember.updateInformationAgreeStatus(dto.getInformationAgreeStatus());

        return findMember;
    }

    //확인: 새로 입력한 폰 인증번호 맞는지 확인
    @Transactional(readOnly = false)
    public void checkPhoneValidKey(int id,GetCheckPhoneValidKeyReq dto){
        // 검증: 전화번호 중복확인
        if(memberRepository.findMemberByPhoneNumber(dto.getPhoneNumber()).isPresent()){
            throw new BaseException(DUPLICATE_USER_PHONENUMBER);
        }

        // 확인: 기존 폰 인증번호 조회 -> dto 번호롸 일치하진 않으면 에러
        Member findMember = memberRepository
                .findById(id)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        if(!dto.getPhoneValidKey().equals(findMember.getPhoneValidKey())){
            throw new BaseException(DIFFERENT_PHONEVALIDKEY);
        }
    }

    // 중복 확인: 닉네임
    @Transactional(readOnly = false)
    public void checkNickNameDuplicated(GetNickNameDuplicatedReq dto){

        //멤버 닉네임으로 조회되면 중복 처리
        if(!memberRepository.findMemberByNickName(dto.getNickName()).isEmpty()){
            throw new BaseException(DUPLICATE_USER_NICKNAME);
        }
    }


    // 닉네임 변경하기
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

    // 회원 탈퇴하기
    @Transactional(readOnly = false)
    public Member UpdateMemberStatus(int id, PatchMemberStatusReq dto) {
        Optional <Member> memberEntity = memberRepository.findMemberById(id);
        // 해당 유저 X
        if(memberRepository.findMemberById(id).isEmpty()){
            throw new BaseException(NOT_EXISTS_PARTICIPANT);
        }
        // 이미 탈퇴한 회원
        if(memberEntity.get().getStatus().toString().equals("INACTIVE")){
            throw new BaseException(ALREADY_INACTIVE_USER);
        }
        // 입력한 두 비밀번호가 다를 때
        if(!dto.getCheckPassword().equals(dto.getPassword())) {
            throw new BaseException(DIFFRENT_PASSWORDS);
        }
        // 입력한 비밀번호가 틀렸을 때
        String password = SHA256.encrypt(dto.getPassword());
        if(!memberEntity.get().getPassword().equals(password)) {
            throw new BaseException(NOT_EXISTS_PASSWORD);
        }

        Member member = memberRepository.findMemberById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. id="+ id));

        member.changeStatusToInactive();
        memberRepository.save(member);
        return member;
    }

    // 비밀번호 수정하기
    @Transactional(readOnly = false)
    public Member UpdatePassword(int id, PatchPasswordReq dto) {
        Optional <Member> memberEntity = memberRepository.findMemberById(id);

        // 해당 유저 X
        if(memberRepository.findMemberById(id).isEmpty()){
            throw new BaseException(NOT_EXISTS_PARTICIPANT);
        }
        // 이미 탈퇴한 회원
        if(memberEntity.get().getStatus().toString().equals("INACTIVE")){
            throw new BaseException(ALREADY_INACTIVE_USER);
        }
        // 입력한 두 비밀번호가 다를 때
        if(!dto.getCheckNewPassword().equals(dto.getNewPassword())) {
            throw new BaseException(DIFFRENT_PASSWORDS);
        }
        // 입력한 기존 비밀번호가 틀렸을 때
        String password = SHA256.encrypt(dto.getPassword());
        if(!memberEntity.get().getPassword().equals(password)) {
            throw new BaseException(NOT_EXISTS_PASSWORD);
        }
        // 새로운 비밀번호가 기존의 비밀번호와 같을 때
        String new_password = SHA256.encrypt(dto.getNewPassword());
        if(memberEntity.get().getPassword().equals(new_password)) {
            throw new BaseException(SAME_PASSWORDS);
        }

        dto.setNewPassword((SHA256.encrypt(dto.getNewPassword())));
        Member member = memberRepository.findMemberById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. id="+ id));

        member.updatePassword(dto.getNewPassword());
        return member;
    }

    // 로그인 아이디 중복 확인하기
    @Transactional(readOnly = false)
    public void checkId(GetCheckIdReq dto) {
        // 아이디가 조회될때
        if(!memberRepository.findMemberByLoginId(dto.getLoginId()).isEmpty()){
            throw new BaseException(EXISTS_LOGIN_ID);
        }
    }


}
