package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.config.status.LoginStatus;
import shop.geeksasang.config.status.ValidStatus;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.*;

import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.member.get.GetCheckCurrentPasswordReq;
import shop.geeksasang.dto.member.get.GetCheckIdReq;
import shop.geeksasang.dto.member.get.GetMemberRes;
import shop.geeksasang.dto.member.get.GetNickNameDuplicatedReq;
import shop.geeksasang.dto.member.patch.*;
import shop.geeksasang.dto.member.post.PostRegisterReq;
import shop.geeksasang.dto.member.post.PostRegisterRes;
import shop.geeksasang.dto.member.post.PostSocialRegisterReq;
import shop.geeksasang.dto.member.post.PostSocialRegisterRes;
import shop.geeksasang.repository.*;
import shop.geeksasang.utils.encrypt.SHA256;
import shop.geeksasang.utils.jwt.JwtService;
import shop.geeksasang.utils.password.PasswordUtils;
import shop.geeksasang.utils.resttemplate.naverlogin.NaverLoginData;
import shop.geeksasang.utils.resttemplate.naverlogin.NaverLoginService;

import java.util.Optional;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final UniversityRepository universityRepository;
    private final EmailRepository emailRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final DormitoryRepository dormitoryRepository;

    private final SmsService smsService;

    private final NaverLoginService naverLoginRequest;
    private final JwtService jwtService;

    // 회원 가입하기
    @Transactional(readOnly = false)
    public PostRegisterRes registerMember(PostRegisterReq dto) {
        if (!dto.getCheckPassword().equals(dto.getPassword())) {
            throw new BaseException(DIFFRENT_PASSWORDS);
        }
        if (!memberRepository.findMemberByLoginId(dto.getLoginId()).isEmpty()) {
            throw new BaseException(DUPLICATE_USER_LOGIN_ID);
        }
        // 검증: 동의여부가 Y 가 이닌 경우
        if (!dto.getInformationAgreeStatus().equals("Y")) {
            throw new BaseException(INVALID_INFORMATIONAGREE_STATUS);
        }
        // 검증: 이메일 인증 여부
        if (memberRepository.findMemberByEmailId(dto.getEmailId()).isPresent()) {
            throw new BaseException(ALREADY_VALID_EMAIL);
        }

        Email email = emailRepository.findById(dto.getEmailId()).orElseThrow(
                () -> new BaseException(INVALID_EMAIL_MEMBER));
        if (!email.getEmailValidStatus().equals(ValidStatus.SUCCESS))
            throw new BaseException(INVALID_EMAIL_MEMBER);

        // 검증: 휴대폰 인증 여부
        PhoneNumber phoneNumber = phoneNumberRepository.findById(dto.getPhoneNumberId()).orElseThrow(
                () -> new BaseException(INVALID_INFORMATIONAGREE_STATUS));
        if (!phoneNumber.getPhoneValidStatus().equals(ValidStatus.SUCCESS))
            throw new BaseException(INVALID_PHONE_NUMBER);


        dto.setPassword(SHA256.encrypt(dto.getPassword()));
        Member member = dto.toEntity(email, phoneNumber);
        University university = universityRepository
                .findUniversityByName(dto.getUniversityName())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_UNIVERSITY));
        member.connectUniversity(university);
        member.changeStatusToActive();
        member.changeLoginStatusToNever(); // 로그인 안해본 상태 디폴트 저장
        member.changeProfileImgUrl("https://geeksasaeng-s3.s3.ap-northeast-2.amazonaws.com/%ED%94%84%EB%A1%9C%ED%95%84+%EC%9D%B4%EB%AF%B8%EC%A7%80/baseProfileImg.png");// S3기본 프로필 이미지 저장
        memberRepository.save(member);
        PostRegisterRes postRegisterRes = PostRegisterRes.toDto(member, email, phoneNumber);
        return postRegisterRes;
    }

    // 소셜 회원가입 하기
    @Transactional(readOnly = false)
    public PostSocialRegisterRes registerSocialMember(PostSocialRegisterReq dto) {
        Email emailEntity;
        // 네이버 사용자 토큰 받아오기
        NaverLoginData naverLoginData = naverLoginRequest.getToken(dto.getAccessToken());
        String naverId = naverLoginData.getId();
        String loginId = naverLoginData.getEmail();
        String phoneNumber = naverLoginData.getMobile();
        phoneNumber = phoneNumber.replace("-", "");
        String email = dto.getEmail();
        String password = naverId;

        if (!memberRepository.findMemberByLoginId(loginId).isEmpty()) {
            throw new BaseException(DUPLICATE_USER_LOGIN_ID);
        }

        // 검증: 동의여부가 Y 가 아닌 경우
        if (!dto.getInformationAgreeStatus().equals("Y")) {
            throw new BaseException(INVALID_INFORMATIONAGREE_STATUS);
        }

        emailEntity = emailRepository.findEmailByAddress(email).orElseThrow(() -> new BaseException(NOT_MATCH_EMAIL));
        int emailId = emailEntity.getId();

        // 검증: 이메일 인증 여부
        if (!memberRepository.findMemberByEmailId(emailId).isEmpty()) {
            throw new BaseException(ALREADY_VALID_EMAIL);
        }
        if (!emailEntity.getEmailValidStatus().equals(ValidStatus.SUCCESS))
            throw new BaseException(INVALID_EMAIL_MEMBER);

        // 검증: 핸드폰 번호가 등록이 안되어있는지
        Optional<PhoneNumber> sa = phoneNumberRepository.findPhoneNumberByNumber(phoneNumber);
        if (phoneNumberRepository.findPhoneNumberByNumber(phoneNumber).isPresent()) {
            throw new BaseException(DUPLICATE_USER_PHONENUMBER);
        }

        // 핸드폰 번호 DB에 저장
        PhoneNumber phoneNumberEntity = smsService.savePhoneNumber(phoneNumber);

        Member member = dto.toEntity(emailEntity, phoneNumberEntity, loginId, password);
        University university = universityRepository
                .findUniversityByName(dto.getUniversityName())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_UNIVERSITY));
        member.connectUniversity(university);
        member.changeStatusToActive();
        member.changeLoginStatusToNever(); // 로그인 안해본 상태 디폴트 저장
        member.changeProfileImgUrl("https://geeksasaeng-s3.s3.ap-northeast-2.amazonaws.com/%ED%94%84%EB%A1%9C%ED%95%84+%EC%9D%B4%EB%AF%B8%EC%A7%80/baseProfileImg.png");// S3기본 프로필 이미지 저장
        memberRepository.save(member);

        // jwt 발급
        JwtInfo vo = JwtInfo.builder()
                .userId(member.getId())
                .universityId(member.getUniversity().getId())
                .build();

        String jwt = jwtService.createJwt(vo);
        PostSocialRegisterRes postSocialRegisterRes = PostSocialRegisterRes.toDto(member, emailEntity, phoneNumberEntity, jwt);
        return postSocialRegisterRes;
    }

    // 수정: 회원정보 동의 수정
    @Transactional(readOnly = false)
    public Member updateInformationAgreeStatus(int id, PatchInformationAgreeStatusReq dto) {

        //멤버 아이디로 조회
        Member findMember = memberRepository
                .findById(id)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        //동의 여부 수정
        findMember.updateInformationAgreeStatus(dto.getInformationAgreeStatus());

        return findMember;
    }

    // 중복 확인: 닉네임
    @Transactional(readOnly = false)
    public void checkNickNameDuplicated(GetNickNameDuplicatedReq dto) {

        //멤버 닉네임으로 조회되면 중복 처리
        if (!memberRepository.findMemberByNickName(dto.getNickName()).isEmpty()) {
            throw new BaseException(DUPLICATE_USER_NICKNAME);
        }
    }

    // 회원 탈퇴하기
    @Transactional(readOnly = false)
    public Member updateMemberStatus(int id, PatchMemberStatusReq dto) {
        Optional<Member> memberEntity = memberRepository.findMemberById(id);
        // 해당 유저 X
        if (memberRepository.findMemberById(id).isEmpty()) {
            throw new BaseException(NOT_EXISTS_PARTICIPANT);
        }
        // 이미 탈퇴한 회원
        if (memberEntity.get().getStatus().toString().equals("INACTIVE")) {
            throw new BaseException(ALREADY_INACTIVE_USER);
        }
        // 입력한 두 비밀번호가 다를 때
        if (!dto.getCheckPassword().equals(dto.getPassword())) {
            throw new BaseException(DIFFRENT_PASSWORDS);
        }
        // 입력한 비밀번호가 틀렸을 때
        String password = SHA256.encrypt(dto.getPassword());
        if (!memberEntity.get().getPassword().equals(password)) {
            throw new BaseException(NOT_EXISTS_PASSWORD);
        }

        Member member = memberRepository.findMemberById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. id=" + id));

        member.changeStatusToInactive();
        memberRepository.save(member);
        return member;
    }

    // 수정: FCM토큰 수정
    @Transactional(readOnly = false)
    public PatchFcmTokenRes updateFcmToken(PatchFcmTokenReq dto, int memberId){
        Member member = memberRepository.findMemberByIdAndStatus(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        member.updateFcmToken(dto.getFcmToken());
        return PatchFcmTokenRes.toDto(member);
    }

    // 로그인 아이디 중복 확인하기
    @Transactional(readOnly = false)
    public void checkId(GetCheckIdReq dto) {
        // 아이디가 조회될때
        if (!memberRepository.findMemberByLoginId(dto.getLoginId()).isEmpty()) {
            throw new BaseException(EXISTS_LOGIN_ID);
        }
    }

    @Transactional(readOnly = false)
    public PatchMemberRes updateMember(PatchMemberReq dto, int memberId) {

        Member member = memberRepository.findMemberByIdAndStatus(memberId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        Dormitory dormitory = dormitoryRepository.findDormitoryById(dto.getDormitoryId()).orElseThrow(() -> new BaseException(NOT_EXISTS_DORMITORY));

        if(PasswordUtils.isNotSamePassword(dto.getNewPassword(), dto.getCheckNewPassword())) {
            throw new BaseException(DIFFRENT_PASSWORDS);
        }

        Member updateMember = member.update(dormitory, dto.getProfileImgUrl(), dto.getNewPassword(), dto.getNickname());

        return PatchMemberRes.toDto(updateMember);
    }

    @Transactional(readOnly = true)
    public GetMemberRes getMember(int memberId){
        // 조회
        Member member = memberRepository.findMemberByIdAndStatus(memberId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));
        // 변환
        return GetMemberRes.toDto(member);
    }

    //체크: 사용자의 입력된 비밀번호 일치확인
    @Transactional(readOnly = true)
    public void checkCurrentPassword(GetCheckCurrentPasswordReq dto, int memberId){
        // 멤버 조회
        Member member = memberRepository.findMemberByIdAndStatus(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));
        // 비밀번호 암호화
        String password = SHA256.encrypt(dto.getPassword());
        // 비밀번호 비교
        if(!password.equals(member.getPassword())){
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_PASSWORD);
        }
        // 반환
    }
}
