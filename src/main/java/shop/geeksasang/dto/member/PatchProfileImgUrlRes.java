package shop.geeksasang.dto.member;

import lombok.Builder;
import lombok.Data;
import shop.geeksasang.domain.Member;

@Builder // .builder() 사용
@Data // Getter, Setter 포함
public class PatchProfileImgUrlRes {

    private int id;
    private String loginId;
    private String nickName;
    private String universityName;
    private String email;
    private String phoneNumber;
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
