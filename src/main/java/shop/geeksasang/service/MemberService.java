package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import shop.geeksasang.config.status.ValidStatus;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.Email;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.PhoneNumber;
import shop.geeksasang.domain.University;

import shop.geeksasang.dto.member.get.GetCheckIdReq;
import shop.geeksasang.dto.member.get.GetNickNameDuplicatedReq;
import shop.geeksasang.dto.member.patch.*;
import shop.geeksasang.dto.member.post.PostRegisterReq;
import shop.geeksasang.dto.member.post.PostRegisterRes;
import shop.geeksasang.dto.member.post.PostSocialRegisterReq;
import shop.geeksasang.dto.member.post.PostSocialRegisterRes;
import shop.geeksasang.repository.EmailRepository;
import shop.geeksasang.repository.MemberRepository;
import shop.geeksasang.repository.PhoneNumberRepository;
import shop.geeksasang.repository.UniversityRepository;
import shop.geeksasang.utils.jwt.RedisUtil;
import shop.geeksasang.utils.encrypt.SHA256;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final UniversityRepository universityRepository;
    private final EmailRepository emailRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    private final SmsService smsService;

    private final RestTemplate restTemplate;

    // 회원 가입하기
    @Transactional(readOnly = false)
    public PostRegisterRes registerMember(PostRegisterReq dto){
         if(!dto.getCheckPassword().equals(dto.getPassword())) {
             throw new BaseException(DIFFRENT_PASSWORDS);
         }
        if(!memberRepository.findMemberByLoginId(dto.getLoginId()).isEmpty()){
            throw new BaseException(DUPLICATE_USER_LOGIN_ID);
        }
        // 검증: 동의여부가 Y 가 이닌 경우
        if(!dto.getInformationAgreeStatus().equals("Y")){
            throw new BaseException(INVALID_INFORMATIONAGREE_STATUS);
        }
        // 검증: 이메일 인증 여부
        if(memberRepository.findMemberByEmailId(dto.getEmailId()).isPresent()){
            throw new BaseException(ALREADY_VALID_EMAIL);
        }

        Email email = emailRepository.findById(dto.getEmailId()).orElseThrow(
                () -> new BaseException(INVALID_EMAIL_MEMBER));
        if(!email.getEmailValidStatus().equals(ValidStatus.SUCCESS))
            throw new BaseException(INVALID_EMAIL_MEMBER);

        // 검증: 휴대폰 인증 여부
        if(memberRepository.findMemberByPhoneNumberId(dto.getPhoneNumberId()).isPresent()){
            throw new BaseException(DUPLICATE_USER_PHONENUMBER);
        }

        PhoneNumber phoneNumber = phoneNumberRepository.findById(dto.getPhoneNumberId()).orElseThrow(
                () -> new BaseException(INVALID_EMAIL_MEMBER));
        if(!phoneNumber.getPhoneValidStatus().equals(ValidStatus.SUCCESS))
            throw new BaseException(INVALID_SMS_MEMBER);

        dto.setPassword(SHA256.encrypt(dto.getPassword()));
        Member member = dto.toEntity(email, phoneNumber);
        University university = universityRepository
                .findUniversityByName(dto.getUniversityName())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_UNIVERSITY));
        member.connectUniversity(university);
        member.changeStatusToActive();
        member.changeLoginStatusToNever(); // 로그인 안해본 상태 디폴트 저장
        memberRepository.save(member);
        PostRegisterRes postRegisterRes = PostRegisterRes.toDto(member, email, phoneNumber);
        return postRegisterRes;
    }

    // 소셜 회원가입 하기
    @Transactional(readOnly = false)
    public PostSocialRegisterRes registerSocialMember(PostSocialRegisterReq dto){
        // 네이버 사용자 토큰 받아오기
        String phoneNumber = null;
        String loginId = null;
        try{
            NaverLoginResponse response = restTemplate.getForObject(dto.getLoginURL(), NaverLoginResponse.class);
            NaverLoginData data = response.getResponse();
            phoneNumber = data.getPhoneNumber();
            loginId = data.getEmail();
        }catch(HttpStatusCodeException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new BaseException(NAVER_LOGIN_ERROR);
            }
        }
        if(phoneNumber == null || loginId == null){
            throw new BaseException(NAVER_LOGIN_ERROR);
        }
        if(!dto.getCheckPassword().equals(dto.getPassword())) {
            throw new BaseException(DIFFRENT_PASSWORDS);
        }
        if(!memberRepository.findMemberByLoginId(dto.getLoginId()).isEmpty()){
            throw new BaseException(DUPLICATE_USER_LOGIN_ID);
        }
        // 검증: 동의여부가 Y 가 이닌 경우
        if(!dto.getInformationAgreeStatus().equals("Y")){
            throw new BaseException(INVALID_INFORMATIONAGREE_STATUS);
        }

        // 검증: 이메일 인증 여부
        if(memberRepository.findMemberByEmailId(dto.getEmailId()).isPresent()){
            throw new BaseException(ALREADY_VALID_EMAIL);
        }

        Email email = emailRepository.findById(dto.getEmailId()).orElseThrow(
                () -> new BaseException(INVALID_EMAIL_MEMBER));
        if(!email.getEmailValidStatus().equals(ValidStatus.SUCCESS))
            throw new BaseException(INVALID_EMAIL_MEMBER);

        // 핸드폰 번호 DB에 저장
        PhoneNumber phoneNumberEntity = smsService.savePhoneNumber(phoneNumber);

        // 비밀번호 암호화
        dto.setPassword(SHA256.encrypt(dto.getPassword()));
        Member member = dto.toEntity(email, phoneNumberEntity);
        University university = universityRepository
                .findUniversityByName(dto.getUniversityName())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_UNIVERSITY));
        member.connectUniversity(university);
        member.changeStatusToActive();
        member.changeLoginStatusToNever(); // 로그인 안해본 상태 디폴트 저장
        memberRepository.save(member);
        PostSocialRegisterRes postSocialRegisterRes = PostSocialRegisterRes.toDto(member, email, phoneNumberEntity);
        return postSocialRegisterRes;
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
