package shop.geeksasang.domain.report.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeliverPartyReportRecord is a Querydsl query type for DeliverPartyReportRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliverPartyReportRecord extends EntityPathBase<DeliverPartyReportRecord> {

    private static final long serialVersionUID = 1210481270L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeliverPartyReportRecord deliverPartyReportRecord = new QDeliverPartyReportRecord("deliverPartyReportRecord");

    public final QReportRecord _super;

    //inherited
    public final StringPath createdAt;

    //inherited
    public final NumberPath<Integer> id;

    // inherited
    public final shop.geeksasang.domain.QMember owner;

    public final shop.geeksasang.domain.QDeliveryParty reportedDeliveryParty;

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status;

    //inherited
    public final StringPath updatedAt;

    public QDeliverPartyReportRecord(String variable) {
        this(DeliverPartyReportRecord.class, forVariable(variable), INITS);
    }

    public QDeliverPartyReportRecord(Path<? extends DeliverPartyReportRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeliverPartyReportRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeliverPartyReportRecord(PathMetadata metadata, PathInits inits) {
        this(DeliverPartyReportRecord.class, metadata, inits);
    }

    public QDeliverPartyReportRecord(Class<? extends DeliverPartyReportRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QReportRecord(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.owner = _super.owner;
        this.reportedDeliveryParty = inits.isInitialized("reportedDeliveryParty") ? new shop.geeksasang.domain.QDeliveryParty(forProperty("reportedDeliveryParty"), inits.get("reportedDeliveryParty")) : null;
        this.status = _super.status;
        this.updatedAt = _super.updatedAt;
    }

}

