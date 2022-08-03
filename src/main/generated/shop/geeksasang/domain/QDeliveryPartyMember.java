package shop.geeksasang.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeliveryPartyMember is a Querydsl query type for DeliveryPartyMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryPartyMember extends EntityPathBase<DeliveryPartyMember> {

    private static final long serialVersionUID = -1003189377L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeliveryPartyMember deliveryPartyMember = new QDeliveryPartyMember("deliveryPartyMember");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QMember participant;

    public final QDeliveryParty party;

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status = _super.status;

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QDeliveryPartyMember(String variable) {
        this(DeliveryPartyMember.class, forVariable(variable), INITS);
    }

    public QDeliveryPartyMember(Path<? extends DeliveryPartyMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeliveryPartyMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeliveryPartyMember(PathMetadata metadata, PathInits inits) {
        this(DeliveryPartyMember.class, metadata, inits);
    }

    public QDeliveryPartyMember(Class<? extends DeliveryPartyMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.participant = inits.isInitialized("participant") ? new QMember(forProperty("participant"), inits.get("participant")) : null;
        this.party = inits.isInitialized("party") ? new QDeliveryParty(forProperty("party"), inits.get("party")) : null;
    }

}

