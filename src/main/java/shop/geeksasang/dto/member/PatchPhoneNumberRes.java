package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;
import shop.geeksasang.domain.Email;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.PhoneNumber;

@Builder // .builder() 사용
@Data// Getter, Setter 포함
public class PatchPhoneNumberRes {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "사용자 인덱스")
    private int id;

    @ApiModelProperty(example = "zero")
    @ApiParam(value = "사용자 로그인 아이디")
    private  String loginId;

    @ApiModelProperty(example = "긱사생1")
    @ApiParam(value = "사용자 닉네임")
    private  String nickname;

    @ApiModelProperty(example = "Gachon University")
    @ApiParam(value = "사용자 대학교")
    private  String universityName;

    @ApiModelProperty(example = "zero@naver.com")
    @ApiParam(value = "사용자 이메일")
    private Email email;

    @ApiModelProperty(example = "01012341234")
    @ApiParam(value = "사용자 폰 번호")
    private PhoneNumber phoneNumber;

    // 빌더
    static public PatchPhoneNumberRes toDto(Member member){
        return PatchPhoneNumberRes.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickname(member.getNickName())
                .universityName(member.getUniversity().toString())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
