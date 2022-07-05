package shop.geeksasang.dto.member;

import lombok.Builder;
import lombok.Data;
import shop.geeksasang.domain.Member;

@Builder // .builder() 사용
@Data // Getter, Setter 포함
public class PatchMemberPhoneValidKeyRes {

    private int id;
    private String loginId;
    private String nickName;
    private String universityName;
    private String email;
    private String phoneNumber;
    private String phoneValidKey;

    // 빌더
    static public PatchMemberPhoneValidKeyRes toDto(Member member){
        return PatchMemberPhoneValidKeyRes.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickName(member.getNickName())
                .universityName(member.getUniversity().toString())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .phoneValidKey(member.getPhoneValidKey())
                .build();
    }


}
