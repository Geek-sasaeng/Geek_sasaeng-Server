package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import shop.geeksasang.domain.Email;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.PhoneNumber;

public class PostSocialRegisterRes {

    @ApiModelProperty(example = "geeksasaeng@gmail.com")
    private  String loginId;

    @ApiModelProperty(example = "긱사생")
    private  String nickname;

    @ApiModelProperty(example = "Gachon University")
    private  String universityName;

    @ApiModelProperty(example = "\"email\": {\n" +
            "            \"createdAt\": \"2022/07/15 10:39:12\",\n" +
            "            \"updatedAt\": \"2022/07/15 10:39:12\",\n" +
            "            \"status\": null,\n" +
            "            \"id\": 2,\n" +
            "            \"address\": \"forceTlight@gachon.ac.kr\",\n" +
            "            \"emailValidStatus\": \"SUCCESS\"\n" +
            "        }")
    @ApiParam(value = "사용자 이메일")
    private Email email;

    @ApiModelProperty(example = "\"phoneNumber\": {\n" +
            "            \"createdAt\": \"2022/07/15 10:40:24\",\n" +
            "            \"updatedAt\": \"2022/07/15 10:40:24\",\n" +
            "            \"status\": null,\n" +
            "            \"id\": 2,\n" +
            "            \"number\": \"01025291674\",\n" +
            "            \"phoneValidStatus\": \"SUCCESS\"\n" +
            "        }")
    @ApiParam(value = "사용자 핸드폰 번호")
    private PhoneNumber phoneNumber;

    static public PostRegisterRes toDto(Member member) {
        return PostRegisterRes.builder()
                .loginId(member.getLoginId())
                .nickname(member.getNickName())
                .universityName(member.getUniversity().getName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
