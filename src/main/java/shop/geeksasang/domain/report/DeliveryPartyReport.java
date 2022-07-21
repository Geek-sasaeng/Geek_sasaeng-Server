package shop.geeksasang.domain.report;

import shop.geeksasang.domain.DeliveryParty;

import javax.persistence.*;


@Entity
@DiscriminatorValue("delivery_party_report")
public class DeliveryPartyReport extends Report {

    @ManyToOne
    @JoinColumn(name = "delivery_party_id")
    private DeliveryParty deliveryParty;
}

/**
 * 게시물 신고
 * 게시글 신고가 3개 이상일 때는, 그 게시글 삭제
 * 이미 신고를 한 게시물에는 다시 신고를 할 수 없음
 */