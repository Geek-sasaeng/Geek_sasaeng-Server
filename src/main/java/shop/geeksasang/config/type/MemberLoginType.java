package shop.geeksasang.config.type;

public enum MemberLoginType {
    NORMAL_USER("일반 로그인 유저"),
    NAVER_USER("네이버 로그인 유저");

    private String description;

    MemberLoginType(String description) {
        this.description = description;
    }
}
