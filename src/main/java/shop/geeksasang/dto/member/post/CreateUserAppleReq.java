package shop.geeksasang.dto.member.post;


import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.type.MemberLoginType;
import shop.geeksasang.domain.member.Grade;
import shop.geeksasang.domain.member.Member;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class CreateUserAppleReq {

    String loginId;
    String refreshToken;
    String name;
    MemberLoginType memberLoginType;

    public CreateUserAppleReq(String refreshToken, MemberLoginType memberLoginType) {
        this.loginId = String.valueOf(UUID.randomUUID());
        this.refreshToken = refreshToken;
        this.name = null;
        this.memberLoginType = memberLoginType;
    }

    public static Member toEntityUserApple(CreateUserAppleReq createUserAppleReq, Grade grade){
        return new Member(
                createUserAppleReq.loginId,
                createUserAppleReq.refreshToken,
                createUserAppleReq.name,
                createUserAppleReq.memberLoginType,
                grade
        );
    }
}
