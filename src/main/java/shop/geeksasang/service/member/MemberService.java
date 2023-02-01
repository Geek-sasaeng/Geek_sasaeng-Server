package shop.geeksasang.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.multipart.MultipartFile;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.config.status.LoginStatus;
import shop.geeksasang.config.status.ValidStatus;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.auth.Email;
import shop.geeksasang.domain.auth.PhoneNumber;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.domain.university.Dormitory;
import shop.geeksasang.domain.university.University;
import shop.geeksasang.dto.deliveryParty.get.GetRecentOngoingPartiesRes;
import shop.geeksasang.dto.dormitory.PatchDormitoryReq;
import shop.geeksasang.dto.dormitory.PatchDormitoryRes;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.member.get.*;
import shop.geeksasang.dto.member.get.vo.DormitoriesVo;
import shop.geeksasang.dto.member.patch.*;
import shop.geeksasang.dto.member.post.*;
import shop.geeksasang.repository.auth.EmailRepository;
import shop.geeksasang.repository.auth.PhoneNumberRepository;
import shop.geeksasang.repository.deliveryparty.query.DeliveryPartyQueryRepository;
import shop.geeksasang.repository.member.MemberRepository;
import shop.geeksasang.repository.university.DormitoryRepository;
import shop.geeksasang.repository.university.UniversityRepository;
import shop.geeksasang.service.common.AwsS3Service;
import shop.geeksasang.service.auth.SmsService;
import shop.geeksasang.utils.encrypt.SHA256;
import shop.geeksasang.utils.jwt.JwtService;
import shop.geeksasang.utils.password.PasswordUtils;
import shop.geeksasang.utils.resttemplate.naverlogin.NaverLoginData;
import shop.geeksasang.utils.resttemplate.naverlogin.NaverLoginService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    private final DeliveryPartyQueryRepository deliveryPartyQueryRepository;

    private final SmsService smsService;

    private final NaverLoginService naverLoginRequest;
    private final JwtService jwtService;

    private final AwsS3Service awsS3Service;

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
        member.changeProfileImgUrl("https://geeksasaeng-s3.s3.ap-northeast-2.amazonaws.com/5bc8d80a-580d-455a-a414-d0d2f9af2c9f-newProfileImg.png");// S3기본 프로필 이미지 저장
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
        member.changeProfileImgUrl("https://geeksasaeng-s3.s3.ap-northeast-2.amazonaws.com/5bc8d80a-580d-455a-a414-d0d2f9af2c9f-newProfileImg.png");// S3기본 프로필 이미지 저장
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

    //수정 : 회원 정보 수정 (마이페이지)
    @Transactional(readOnly = false)
    public PostMemberInfoRes updateMember(PostMemberInfoReq dto, int memberId) throws IOException {

        Member member = memberRepository.findMemberById(memberId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        String imgUrl = member.getProfileImgUrl(); //프로필 이미지 url

        //aws에 업로드
        if(!dto.getProfileImg().isEmpty()){
            MultipartFile image = dto.getProfileImg();
            imgUrl = awsS3Service.upload(image.getInputStream(),image.getOriginalFilename(), image.getSize());
        }

        //선택한 기숙사 validation
        Dormitory dormitory = dormitoryRepository.findDormitoryById(dto.getDormitoryId())
                .orElseThrow(() ->  new BaseException(BaseResponseStatus.NOT_EXISTS_DORMITORY));

        //수정
        Member updateMember = member.update(dto,imgUrl,dormitory);

        return PostMemberInfoRes.toDto(updateMember);
    }

    @Transactional(readOnly = true)
    public GetMemberRes getMember(int memberId){
        // 조회
        Member member = memberRepository.findMemberByIdAndStatus(memberId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));
        // 변환
        List<DeliveryParty> threeRecentDeliveryParty = deliveryPartyQueryRepository.findRecentOngoingDeliveryParty(memberId);
        List<GetRecentOngoingPartiesRes> partiesRes = threeRecentDeliveryParty.stream()
                .map(deliveryParty -> GetRecentOngoingPartiesRes.toDto(deliveryParty))
                .collect(Collectors.toList());

        return GetMemberRes.toDto(member, partiesRes);
    }

    //조회 : 회원 정보 조회 (마이페이지)
    @Transactional(readOnly = true)
    public GetMemberInfoRes getMemberInfo(int memberId){
        Member member = memberRepository.findMemberById(memberId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        List<Dormitory> dormitoryList = member.getUniversity().getDormitories();

        List<DormitoriesVo> dormitoriesVoList = dormitoryList.stream()
                .map(dormitory -> new DormitoriesVo(dormitory))
                .collect(Collectors.toList());

        return GetMemberInfoRes.toDto(member,dormitoriesVoList);
    }


    //체크: 사용자의 입력된 비밀번호 일치확인
    @Transactional(readOnly = true)
    public void checkCurrentPassword(GetCheckCurrentPasswordReq dto, int memberId) {
        Member member = memberRepository.findMemberByIdAndStatus(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));
        // 비밀번호 암호화
        String password = SHA256.encrypt(dto.getPassword());
        // 비밀번호 비교
        if (!password.equals(member.getPassword())) {
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_PASSWORD);
        }
    }

    // 수정: 기숙사 수정하기
    @Transactional(readOnly = false)
    public PatchDormitoryRes updateDormitory(PatchDormitoryReq dto, JwtInfo jwtInfo) {
        int memberId = jwtInfo.getUserId();

        Member member = memberRepository.findMemberByIdAndStatus(memberId).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        // 처음 로그인 시 loginStatus를 NEVER -> NOTNEVER
        if (member.getLoginStatus().equals(LoginStatus.NEVER)) {
            member.changeLoginStatusToNotNever();
        }

        // 수정할 기숙사 조회
        Dormitory dormitory = dormitoryRepository.findDormitoryById(dto.getDormitoryId())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_DORMITORY));
        member.updateDormitory(dormitory);
        return PatchDormitoryRes.toDto(member);
    }

    //비밀번호 바꾸기
    @Transactional(readOnly = false)
    public void changePassword(PatchPasswordReq dto, int memberId) {
        Member member = memberRepository.findMemberByIdAndStatus(memberId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        if(PasswordUtils.isNotSamePassword(dto.getNewPassword(), dto.getCheckNewPassword())){
            throw new BaseException(DIFFRENT_PASSWORDS);
        }
        member.updatePassword(SHA256.encrypt(dto.getNewPassword()));
    }

    //비밀번호 정규식 판별 메소드
    private boolean validatePassword(String pw){
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$");
        Matcher m = p.matcher(pw);
        if(m.matches()){
            return true;
        }
        return false;
    }


    // 애플 유저 가입
    @Transactional(readOnly = false)
    public Member createUserApple(CreateUserAppleReq createUserAppleReq){

        if(memberRepository.existsByLoginId(createUserAppleReq.getLoginId())) throw new BaseException(DUPLICATE_USER_LOGIN_ID);

        //User user = userRepository.save(CreateUserAppleReq.toEntityUserApple(createUserAppleReq));
        Member user = memberRepository.save(CreateUserAppleReq.toEntityUserApple(createUserAppleReq));
        return user;
    }


    /**
     * 리프레시 토큰 검증
     *
     * refresh_token은 만료되지 않기 때문에 권한이 필요한 요청일 경우
     * 굳이 매번 애플 ID 서버로부터 refresh_token을 통해 access_token을 발급 받기보다는
     * 유저의 refresh_token을 따로 DB나 기타 저장소에 저장해두고 캐싱해두고 조회해서 검증하는편이 성능면에서 낫다는 자료를 참고
     * https://hwannny.tistory.com/71
     */
    @Transactional(readOnly = true)
    public Void validateRefreshToken(int userId, String refreshToken){
        Member user = memberRepository.findMemberByIdAndStatus(userId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        if(user.getAppleRefreshToken() == null) throw new BaseException(INVALID_APPLE_REFRESHTOKEN);

        if(!user.getAppleRefreshToken().equals(refreshToken)) throw new BaseException(INVALID_APPLE_REFRESHTOKEN);

        return null;
    }

    public GetMemberDormitoryRes getMemberDormitory(int userId) {
        Member member = memberRepository.findMemberByIdAndStatus(userId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        return GetMemberDormitoryRes.of(member.getDormitory());
    }
}
