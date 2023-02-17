package shop.geeksasang.dto.member.post;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.type.MemberLoginType;
import shop.geeksasang.domain.member.Grade;
import shop.geeksasang.domain.member.Member;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserAppleReq {

    String loginId;
    String refreshToken;
    String name;
    //UserType userType;
    MemberLoginType memberLoginType;

    public static Member toEntityUserApple(CreateUserAppleReq createUserAppleReq, Grade grade){
        Member user = new Member(
                createUserAppleReq.loginId,
                createUserAppleReq.refreshToken,
                createUserAppleReq.name,
                createUserAppleReq.memberLoginType,
                grade
        );
        return user;
    }
}
