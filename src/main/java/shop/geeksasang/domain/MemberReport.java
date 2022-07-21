package shop.geeksasang.domain;


import shop.geeksasang.config.status.ReportStatus;

import javax.persistence.*;

@Entity
@DiscriminatorValue("member_report")
public  class MemberReport extends Report {

}
/**
 * 사용자 신고
 * 사용자 신고가 1달 3개 이상일때는, 사용자가 게시물을 못올림
 * 1달마다 사용자 신고 카운트가 초기화가 됨
 * 사용자 차단하면 신고자에게 그 사용자가 쓴 게시물이 안보임
 * 신고자가 같은 사용자를 여러번 신고할 수 없음
 */

