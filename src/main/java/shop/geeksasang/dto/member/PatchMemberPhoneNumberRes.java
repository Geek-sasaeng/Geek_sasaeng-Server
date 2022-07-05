package shop.geeksasang.dto.member;

import lombok.Builder;
import lombok.Data;
import shop.geeksasang.domain.Member;

@Builder // .builder() 사용
@Data// Getter, Setter 포함
public class PatchMemberPhoneNumberRes {

    private int id;
    private  String loginId;
    private  String nickname;
    private  String universityName;
    private  String email;
    private  String phoneNumber;

    // 빌더
    static public PatchMemberPhoneNumberRes toDto(Member member){
        return PatchMemberPhoneNumberRes.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickname(member.getNickName())
                .universityName(member.getUniversity().toString())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
