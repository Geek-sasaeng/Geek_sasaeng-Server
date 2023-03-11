package shop.geeksasang.domain.deliveryparty;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeliveryParty is a Querydsl query type for DeliveryParty
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryParty extends EntityPathBase<DeliveryParty> {

    private static final long serialVersionUID = -534666807L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeliveryParty deliveryParty = new QDeliveryParty("deliveryParty");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    public final StringPath accountNumber = createString("accountNumber");

    public final StringPath bank = createString("bank");

    public final StringPath chatRoomName = createString("chatRoomName");

    public final shop.geeksasang.domain.member.QMember chief;

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Integer> currentMatching = createNumber("currentMatching", Integer.class);

    public final ListPath<DeliveryPartyHashTag, QDeliveryPartyHashTag> deliveryPartyHashTags = this.<DeliveryPartyHashTag, QDeliveryPartyHashTag>createList("deliveryPartyHashTags", DeliveryPartyHashTag.class, QDeliveryPartyHashTag.class, PathInits.DIRECT2);

    public final ListPath<DeliveryPartyMember, QDeliveryPartyMember> deliveryPartyMembers = this.<DeliveryPartyMember, QDeliveryPartyMember>createList("deliveryPartyMembers", DeliveryPartyMember.class, QDeliveryPartyMember.class, PathInits.DIRECT2);

    public final ListPath<shop.geeksasang.domain.report.DeliveryPartyReport, shop.geeksasang.domain.report.QDeliveryPartyReport> deliveryPartyReports = this.<shop.geeksasang.domain.report.DeliveryPartyReport, shop.geeksasang.domain.report.QDeliveryPartyReport>createList("deliveryPartyReports", shop.geeksasang.domain.report.DeliveryPartyReport.class, shop.geeksasang.domain.report.QDeliveryPartyReport.class, PathInits.DIRECT2);

    public final shop.geeksasang.domain.university.QDormitory dormitory;

    public final QFoodCategory foodCategory;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final shop.geeksasang.domain.location.QLocation location;

    public final EnumPath<shop.geeksasang.config.status.MatchingStatus> matchingStatus = createEnum("matchingStatus", shop.geeksasang.config.status.MatchingStatus.class);

    public final NumberPath<Integer> maxMatching = createNumber("maxMatching", Integer.class);

    public final EnumPath<shop.geeksasang.config.status.OrderStatus> orderStatus = createEnum("orderStatus", shop.geeksasang.config.status.OrderStatus.class);

    public final DateTimePath<java.time.LocalDateTime> orderTime = createDateTime("orderTime", java.time.LocalDateTime.class);

    public final EnumPath<shop.geeksasang.config.type.OrderTimeCategoryType> orderTimeCategory = createEnum("orderTimeCategory", shop.geeksasang.config.type.OrderTimeCategoryType.class);

    public final NumberPath<Integer> reportedCount = createNumber("reportedCount", Integer.class);

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status = _super.status;

    public final StringPath storeUrl = createString("storeUrl");

    public final StringPath title = createString("title");

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public final StringPath uuid = createString("uuid");

    public QDeliveryParty(String variable) {
        this(DeliveryParty.class, forVariable(variable), INITS);
    }

    public QDeliveryParty(Path<? extends DeliveryParty> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeliveryParty(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeliveryParty(PathMetadata metadata, PathInits inits) {
        this(DeliveryParty.class, metadata, inits);
    }

    public QDeliveryParty(Class<? extends DeliveryParty> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chief = inits.isInitialized("chief") ? new shop.geeksasang.domain.member.QMember(forProperty("chief"), inits.get("chief")) : null;
        this.dormitory = inits.isInitialized("dormitory") ? new shop.geeksasang.domain.university.QDormitory(forProperty("dormitory"), inits.get("dormitory")) : null;
        this.foodCategory = inits.isInitialized("foodCategory") ? new QFoodCategory(forProperty("foodCategory")) : null;
        this.location = inits.isInitialized("location") ? new shop.geeksasang.domain.location.QLocation(forProperty("location")) : null;
    }

}

