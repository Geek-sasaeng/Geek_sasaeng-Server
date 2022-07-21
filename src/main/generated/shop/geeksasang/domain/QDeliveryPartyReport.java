package shop.geeksasang.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeliveryPartyReport is a Querydsl query type for DeliveryPartyReport
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryPartyReport extends EntityPathBase<DeliveryPartyReport> {

    private static final long serialVersionUID = -859941351L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeliveryPartyReport deliveryPartyReport = new QDeliveryPartyReport("deliveryPartyReport");

    public final QReport _super;

    //inherited
    public final StringPath content;

    //inherited
    public final StringPath createdAt;

    public final QDeliveryParty deliveryParty;

    //inherited
    public final NumberPath<Integer> id;

    // inherited
    public final QReportCategory reportCategory;

    // inherited
    public final QMember reportedMember;

    // inherited
    public final QMember reportingMember;

    //inherited
    public final EnumPath<shop.geeksasang.config.status.ReportStatus> reportStatus;

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status;

    //inherited
    public final StringPath updatedAt;

    public QDeliveryPartyReport(String variable) {
        this(DeliveryPartyReport.class, forVariable(variable), INITS);
    }

    public QDeliveryPartyReport(Path<? extends DeliveryPartyReport> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeliveryPartyReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeliveryPartyReport(PathMetadata metadata, PathInits inits) {
        this(DeliveryPartyReport.class, metadata, inits);
    }

    public QDeliveryPartyReport(Class<? extends DeliveryPartyReport> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QReport(type, metadata, inits);
        this.content = _super.content;
        this.createdAt = _super.createdAt;
        this.deliveryParty = inits.isInitialized("deliveryParty") ? new QDeliveryParty(forProperty("deliveryParty"), inits.get("deliveryParty")) : null;
        this.id = _super.id;
        this.reportCategory = _super.reportCategory;
        this.reportedMember = _super.reportedMember;
        this.reportingMember = _super.reportingMember;
        this.reportStatus = _super.reportStatus;
        this.status = _super.status;
        this.updatedAt = _super.updatedAt;
    }

}

