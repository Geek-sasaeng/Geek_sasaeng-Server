package shop.geeksasang.domain.deliveryparty;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeliveryPartyHashTag is a Querydsl query type for DeliveryPartyHashTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryPartyHashTag extends EntityPathBase<DeliveryPartyHashTag> {

    private static final long serialVersionUID = -1567664733L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeliveryPartyHashTag deliveryPartyHashTag = new QDeliveryPartyHashTag("deliveryPartyHashTag");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final QDeliveryParty deliveryParty;

    public final QHashTag hashTag;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status = _super.status;

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QDeliveryPartyHashTag(String variable) {
        this(DeliveryPartyHashTag.class, forVariable(variable), INITS);
    }

    public QDeliveryPartyHashTag(Path<? extends DeliveryPartyHashTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeliveryPartyHashTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeliveryPartyHashTag(PathMetadata metadata, PathInits inits) {
        this(DeliveryPartyHashTag.class, metadata, inits);
    }

    public QDeliveryPartyHashTag(Class<? extends DeliveryPartyHashTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deliveryParty = inits.isInitialized("deliveryParty") ? new QDeliveryParty(forProperty("deliveryParty"), inits.get("deliveryParty")) : null;
        this.hashTag = inits.isInitialized("hashTag") ? new QHashTag(forProperty("hashTag")) : null;
    }

}

