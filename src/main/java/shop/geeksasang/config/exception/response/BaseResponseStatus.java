package shop.geeksasang.config.exception.response;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {

    /**
     * 1000 : 요청 성공
     */

    /**
     * 데빈
     * code : 1000~1199
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    SMS_SEND_SUCCESS(true, 1001, "SMS 요청에 성공했습니다."),
    SMS_VERIFICATION_SUCCESS(true, 1002, "SMS 인증에 성공했습니다."),

    /**
     * 토마스최
     * code : 1200~1399
     */
    VALID_PHONEVALIDKEY(true,1201,"폰 인증번호가 일치합니다."),
    VALID_NICKNAME(true,1202,"사용 가능한 닉네임입니다."),

    /**
     * 미니
     * code : 1400~1599
     */

    /**
     * 제로
     * code : 1600~1799
     */
    VALID_ID(true, 1601, "사용가능한 아이디입니다"),

    /**
     * 네오
     * code : 1800~1999
     */
    VALID_EMAIL_NUMBER(true, 1801,"이메일 인증이 완료되었습니다."),
    SEND_MAIL_SUCCESS(true, 1802,"이메일이 성공적으로 전송 되었습니다."),


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
    INVALID_SMS_VERIFY_NUMBER(false,2013,"인증번호가 틀렸습니다."),
    INVALID_SMS_COUNT(false,2015,"일일 최대 전송 횟수를 초과했습니다."),
    INVALID_SMS_UUID(false,2016,"이메일 인증을 하지 못한 유저입니다. 이메일 인증을 해주세요."),
    INVALID_REPORT_COUNT(false, 2017, "하루 신고 최대 횟수를 초과하셨습니다."),
    EXIST_REPORT_RECORD(false, 2018, "중복 신고는 불가능합니다."),
    NOT_EXISTS_REPORT_CATEGORY(false, 2019, "존재하지 않는 신고 카테고리입니다."),
    ALREADY_PARTICIPATE_PARTY(false, 2020, "이미 파티에 참여하고 있습니다."),

    /**
     * 토마스최
     * code : 2200~2399
     */
    INVALID_INFORMATIONAGREE_STATUS(false,2201,"회원 정보동의 status가 Y가 아닙니다."),
    DUPLICATE_USER_PHONENUMBER(false, 2202, "이미 등록된 전화번호 입니다."),
    DIFFERENT_PHONEVALIDKEY(false, 2203, "폰 인증번호가 다릅니다."),
    NOT_EXIST_USER(false,2204,"존재하지 않는 회원 id 입니다."),
    BLANK_KEYWORD(false,2205,"검색어를 입력해주세요"),



    /**
     * 미니
     * code : 2400~2599
     */
    NOT_EXISTS_LOGINID(false,2400,"존재하지 않는 아이디입니다."),
    NOT_EXISTS_HASHTAG(false,2401,"존재하지 않는 해시태그입니다."),
    NOT_EXISTS_CATEGORY(false,2402,"존재하지 않는 카테고리입니다."),
    NOT_EXISTS_PERMISSION_UPDATE(false,2403,"수정권한이 없는 유저입니다."),
    CAN_NOT_CREATE_PARTY(false, 2404, "사용자 신고 3번 이상인 유저는 배달파티를 생성할 수 없습니다."),



    /**
     * 제로
     * code : 2600~2799
     */
    DUPLICATE_USER_NICKNAME(false, 2600, "중복된 닉네임입니다."),
    ALREADY_INACTIVE_USER(false, 2601, "이미 탈퇴한 회원입니다."),
    SAME_PASSWORDS(false, 2602, "기존 비밀번호와 동일합니다."),
    EXISTS_LOGIN_ID(false, 2603, "중복된 아이디입니다."),
    NOT_SPECIFIED_VALUE(false, 2604, "지정된 값이 아닙니다."),
    NOT_EXISTS_UNIVERSITY_ID(false, 2605, "존재하지 않는 학교입니다."),
    NOT_EXISTS_DORMITORY(false, 2606, "기숙사가 존재하지 않습니다."),
    ALREADY_VALID_EMAIL(false, 2607, "이미 인증된 이메일 입니다."),
    NOT_EXISTS_ORDER_TIME_CATEGORY(false, 2608, "존재하지 않는 시간 카테고리 입니다."),
    ALREADY_INACTIVE_DELIVERY_PARTY(false, 2609, "이미 삭제된 배달파티 입니다."),
    DIFFERENT_USER_ID(false, 2610, "파티의 생성 유저가 아닙니다."),
    CAN_NOT_PARTICIPATE(false, 2611, "참여할 수 없는 파티입니다."),
    CAN_NOT_DELETE_PARTY(false, 2612, "삭제할 수 없는 파티입니다."),
    ALREADY_PARTICIPATE_ANOTHER_PARTY(false, 2613, "이미 다른 파티에 참여하고 있습니다."),
    MATCHING_COMPLITED(false, 2614, "매칭이 완료된 파티입니다."),
    ORDER_TIME_OVER(false, 2615, "파티 신청 시간이 끝났습니다."),
    CAN_NOT_FINISH_DELIVERY_PARTY(false, 2616, "파티 매칭 마감을 할 수 없는 유저이거나 이미 마감된 상태입니다."),

    /**
     * 네오
     * code : 2800~2999
     */
    INVALID_EMAIL_NUMBER(false, 2800,"유효하지 않은 인증번호 입니다."),
    NOT_MATCH_EMAIL(false, 2803,"이메일이 주소가 올바르지 않습니다."),
    INVALID_EMAIL_COUNT(false,2804,"이메일 인증은 하루 최대 10번입니다. 내일 다시 시도해주세요."),
    THREAD_OVER_REQUEST(false, 2805, "잠시 후에 다시 시도해주세요"),
    INVALID_EMAIL_MEMBER(false, 2805, "이메일 인증을 하고 회원가입을 시도해주세요."),
    INVALID_SMS_MEMBER(false, 2805, "SMS 인증을 하고 회원가입을 시도해주세요."),
    NAVER_LOGIN_ERROR(false, 2806, "네이버 로그인 토큰 요청에 실패했습니다."),
    INVALID_PHONE_NUMBER(false, 2806, "인증이 안된 핸드폰 번호입니다."),
    MOVE_NAVER_REGISTER(false, 2807, "존재하지 않는 아이디 입니다. 네이버 회원가입 화면으로 이동합니다."),
    NOT_TYPE_NAVER_USER(false, 2808, "위 아이디는 네이버 유저가 아닙니다."),

    /**
     * 3000 : Response 오류
     */


    /**
     * 4000 : Database, Server 오류
     */

    /**
     * 데빈
     * code : 4000~4199
     */
    INTERNAL_SERVER_ERROR(false,4000,"서버 오류입니다."),
    SMS_API_ERROR(false,4001,"SMS API 연동 오류입니다."),
    FAIL_MAKE_SIGNATURE(false,4002,"SMS API 연동 준비 오류입니다.");



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