package shop.geeksasang.dto.member.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.status.LoginStatus;
import shop.geeksasang.config.type.MemberLoginType;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.dto.deliveryParty.get.GetRecentOngoingPartiesRes;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class GetMemberRes {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "사용자 id(pk)")
    private int id;

    @ApiModelProperty(example = "geeksasaeng")
    @ApiParam(value = "사용자 ID")
    private  String loginId;

    @ApiModelProperty(example = "긱사생")
    @ApiParam(value = "사용자 닉네임")
    private  String nickname;

    @ApiModelProperty(example = "1")
    @ApiParam(value = "사용자 대학교id (pk)")
    private int universityId;

    @ApiModelProperty(example = "Gachon University")
    @ApiParam(value = "사용자 대학교")
    private String universityName;

    @ApiModelProperty(example = "1")
    @ApiParam(value = "사용자 이메일id (pk)")
    private int emailId;

    @ApiModelProperty(example = "forceTlight1@gachon.ac.kr")
    @ApiParam(value = "사용자 이메일")
    private String emailAddress;

    @ApiModelProperty(example = "01012341234")
    @ApiParam(value = "사용자 폰 번호")
    private String phoneNumber;

    @ApiModelProperty(example = "http://geeksasaeng.shop/s3/neo.jpg")
    @ApiParam(value = "수정할 프로필 이미지 url")
    private String profileImgUrl;

    @ApiModelProperty(example = "Y")
    @ApiParam(value = "회원 정보동의 여부")
    private String informationAgreeStatus;

    @ApiModelProperty(example = "1")
    @ApiParam(value = "사용자 기숙사id (pk)")
    private int dormitoryId;

    @ApiModelProperty(example = "1기숙사")
    @ApiParam(value = "사용자 기숙사 이름")
    private String dormitoryName;

    @ApiModelProperty(example = "NOTNEVER")
    @ApiParam(value = "사용자 로그인 횟수 상태")
    private LoginStatus loginStatus; // 첫 번째 로그인인지 아닌지

    @ApiModelProperty(example = "NORMAL_USER")
    @ApiParam(value = "사용자 일반 또는 소셜계정 사용자 타입")
    private MemberLoginType memberLoginType;

    @ApiModelProperty(example = "1")
    @ApiParam(value = "사용자 하루 신고 횟수")
    private int perDayReportingCount;

    @ApiModelProperty(example = "2")
    @ApiParam(value = "사용자 신고 당한 횟수")
    private int reportedCount;

    @ApiModelProperty(example = "토큰예시 발급받으면 입력할 예정")
    @ApiParam(value = "FCM 토큰값")
    private String fcmToken;

    @ApiModelProperty(value = "최근 진행 파티 정보")
    List<GetRecentOngoingPartiesRes> parties = new ArrayList<>();

    @ApiModelProperty(example = "2022-07-11 15:30:00", value = "멤버 가입 시각")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String createdAt;

    @ApiModelProperty(example = "복학생", value = "현재 멤버 등급")
    private String grade;

    @ApiModelProperty(example = "졸업까지 5학점 남았어요", value = "다음 등급과 남은 학점 표시")
    private String nextGradeAndRemainCredits;


    //빌더
    static public GetMemberRes toDto(Member member, List<GetRecentOngoingPartiesRes> parties,String nextGradeAndRemainCredits){
        return GetMemberRes.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickname(member.getNickName())
                .universityId(member.getUniversity().getId())
                .universityName(member.getUniversity().getName())
                .emailId(member.getEmail().getId())
                .emailAddress(member.getEmail().getAddress())
                .phoneNumber(member.getPhoneNumber().getNumber())
                .profileImgUrl(member.getProfileImgUrl())
                .informationAgreeStatus(member.getInformationAgreeStatus())
                .dormitoryId(member.getDormitory().getId())
                .dormitoryName(member.getDormitory().getName())
                .loginStatus(member.getLoginStatus())
                .memberLoginType(member.getMemberLoginType())
                .perDayReportingCount(member.getPerDayReportingCount())
                .reportedCount(member.getReportedCount())
                .fcmToken(member.getFcmToken())
                .parties(parties)
                .createdAt(member.getCreatedAt())
                .grade(member.getGrade().getName())
                .nextGradeAndRemainCredits(nextGradeAndRemainCredits)
                .build();
    }

    @Builder
    public GetMemberRes(int id, String loginId, String nickname, int universityId, String universityName, int emailId, String emailAddress, String phoneNumber, String profileImgUrl,
                        String informationAgreeStatus, int dormitoryId, String dormitoryName, LoginStatus loginStatus, MemberLoginType memberLoginType,
                        int perDayReportingCount, int reportedCount, String fcmToken, List<GetRecentOngoingPartiesRes> parties, String createdAt,String grade, String nextGradeAndRemainCredits) {
        this.id = id;
        this.loginId = loginId;
        this.nickname = nickname;
        this.universityId = universityId;
        this.universityName = universityName;
        this.emailId = emailId;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.profileImgUrl = profileImgUrl;
        this.informationAgreeStatus = informationAgreeStatus;
        this.dormitoryId = dormitoryId;
        this.dormitoryName = dormitoryName;
        this.loginStatus = loginStatus;
        this.memberLoginType = memberLoginType;
        this.perDayReportingCount = perDayReportingCount;
        this.reportedCount = reportedCount;
        this.fcmToken = fcmToken;
        this.parties = parties;
        this.createdAt = createdAt;
        this.grade = grade;
        this.nextGradeAndRemainCredits = nextGradeAndRemainCredits;
    }
}
