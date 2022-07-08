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
    EMPTY_JWT(false, 2001, "header에 JWT가 없습니다."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다. 재로그인 바랍니다."),
    EXPIRED_JWT(false, 2003,"만료기간이 지난 JWT입니다. 재로그인 바랍니다."),
    INVALID_USER_JWT(false,2004,"권한이 없는 유저의 접근입니다."),
    DIFFRENT_PASSWORDS(false,2005, "입력하신 두 비밀번호가 다릅니다."),
    DUPLICATE_USER_LOGIN_ID(false, 2006, "중복되는 유저 아이디입니다"),
    DUPLICATE_USER_EMAIL(false, 2007, "중복되는 유저 이메일입니다"),
    NOT_EXISTS_UNIVERSITY(false, 2008, "존재하지 않는 학교 이름입니다"),
    NOT_EXISTS_PARTICIPANT(false,2009,"존재하지 않는 멤버입니다"),
    NOT_EXISTS_PARTY(false,2010,"존재하지 않는 파티입니다"),
    NOT_EXISTS_PASSWORD(false, 2011,"비밀번호가 틀립니다. "),
    INACTIVE_STATUS(false,2012,"회원 status가 INACITVE 입니다."),
    INVALID_SMS_VERIFY_NUMBER(false,2013,"SMS 인증 번호가 다릅니다."),
    INVALID_SMS_PHONE_NUMBER(false,2014,"입력하신 핸드폰 번호와 다릅니다."),
    INVALID_SMS_COUNT(false,2015,"SMS 인증은 하루 최대 5번입니다 내일 시도해주세요."),
    INVALID_SMS_CLIENT_IP(false,2016,"인증번호 재전송을 눌러주세요."),


    /**
     * 토마스최
     * code : 2200~2399
     */
    INVALID_INFORMATIONAGREE_STATUS(false,2201,"회원 정보동의 status가 Y가 아닙니다."),
    VALID_NICKNAME(true,2202,"사용 가능한 닉네임 입니다."),
    DUPLICATE_USER_PHONENUMBER(false, 2203, "이미 등록된 전호번호입니다."),
    DIFFERENT_PHONEVALIDKEY(false, 2204, "폰 인증번호가 다릅니다."),
    NOT_EXIST_USER(false,2205,"회원 id가 존재하지 않습니다."),
    VALID_PHONEVALIDKEY(true,2206,"폰 인증번호가 일치합니다."),

    /**
     * 미니
     * code : 2400~2599
     */
    NOT_EXISTS_LOGINID(false,2400,"존재하지 않는 아이디입니다."),



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
    INVALID_EMAIL_NUMBER(false, 2800,"유효하지 않은 인증번호 입니다."),
    VALID_EMAIL_NUMBER(true, 2801,"이메일 인증이 완료되었습니다."),
    SEND_MAIL_SUCCESS(true, 2802,"이메일이 성공적으로 전송 되었습니다."),
    NOT_MATCH_EMAIL(false, 2803,"이메일이 주소가 올바르지 않습니다."),
    INVALID_IP(false, 2803,"등록되지 않은 아이피 주소입니다."),
    INVALID_EMAIL_COUNT(false,2015,"이메일 인증은 하루 최대 10번입니다. 내일 다시 시도해주세요."),

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