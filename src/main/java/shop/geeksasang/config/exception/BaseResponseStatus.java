package shop.geeksasang.config.exception;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {

    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */

    /**
     * 데빈
     * code : 2000~2199
     */
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    EXPIRED_JWT(false, 2003,"만료기간이 지난 JWT입니다."),
    INVALID_USER_JWT(false,2004,"권한이 없는 유저의 접근입니다."),
    DIFFRENT_PASSWORDS(false,2005, "입력하신 두 비밀번호가 다릅니다."),
    DUPLICATE_USER_LOGIN_ID(false, 2006, "중복되는 유저 아이디입니다"),
    DUPLICATE_USER_EMAIL(false, 2007, "중복되는 유저 이메일입니다"),
    NOT_EXISTS_UNIVERSITY(false, 2008, "존재하지 않는 학교 이름입니다"),
    NOT_EXISTS_PARTICIPANT(false,2009,"존재하지 않는 멤버입니다"),
    NOT_EXISTS_PARTY(false,2010,"존재하지 않는 파티입니다"),
    NOT_EXISTS_PASSWORD(false, 2011,"비밀번호가 틀립니다. "),
    INACTIVE_STATUS(false,2012,"회원 status가 INACITVE 입니다."),
    INVALID_SMS_VERIFY_NUMBER(false,2199,"SMS 인증 번호가 다릅니다."),

    /**
     * 토마스최
     * code : 2200~2399
     */


    /**
     * 미니
     * code : 2400~2599
     */


    /**
     * 제로
     * code : 2600~2799
     */
    DUPLICATE_USER_NICKNAME(false, 2600, "중복되는 유저 닉네임입니다"),
    ALREADY_INACTIVE_USER(false, 2601, "이미 탈퇴한 회원입니다"),
    SAME_PASSWORDS(false, 2602, "기존 비밀번호와 동일합니다"),

    /**
     * 네오
     * code : 2800~2999
     */


    /**
     * 3000 : Response 오류
     */


    /**
     * 4000 : Database, Server 오류
     */


    INTERNAL_SERVER_ERROR(false,4000,"서버 오류입니다."),
    SMS_API_ERROR(false,4001,"SMS 네이버 API 연동 오류입니다.");



    // 5000 : 필요시 만들어서 쓰세요


    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}