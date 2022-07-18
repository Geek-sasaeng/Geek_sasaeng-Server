package shop.geeksasang.dto.member.patch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;
import shop.geeksasang.domain.Email;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.PhoneNumber;

@Builder // .builder() 사용
@Data // Getter, Setter 포함
public class PatchProfileImgUrlRes {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "사용자 인덱스")
    private int id;

    @ApiModelProperty(example = "zero")
    @ApiParam(value = "사용자 로그인 아이디")
    private String loginId;

    @ApiModelProperty(example = "긱사생1")
    @ApiParam(value = "사용자 닉네임")
    private String nickName;

    @ApiModelProperty(example = "Gachon University")
    @ApiParam(value = "사용자 대학교")
    private String universityName;

    @ApiModelProperty(example = "zero@naver.com")
    @ApiParam(value = "사용자 이메일")
    private Email email;

    @ApiModelProperty(example = "01012341234")
    @ApiParam(value = "사용자 폰 번호")
    private PhoneNumber phoneNumber;

    @ApiModelProperty(example = "http://geeksasaeng.shop/s3/neo.jpg")
    @ApiParam(value = "수정할 프로필 이미지 url")
    private String profileImgUrl;

    //빌더
    static public PatchProfileImgUrlRes toDto(Member member){
        return PatchProfileImgUrlRes.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickName(member.getNickName())
                .universityName(member.getUniversity().toString())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .profileImgUrl(member.getProfileImgUrl())
                .build();
    }
}
