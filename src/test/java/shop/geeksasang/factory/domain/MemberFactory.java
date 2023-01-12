package shop.geeksasang.factory.domain;

import shop.geeksasang.domain.member.Member;

public class MemberFactory {
    public static Member create(){
        return new Member(1, "hello");
    }
}
