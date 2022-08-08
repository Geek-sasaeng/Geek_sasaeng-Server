package shop.geeksasang.factory.domain;

import shop.geeksasang.domain.Member;

public class MemberFactory {
    public static Member create(){
        return new Member(1, "hello");
    }
}
