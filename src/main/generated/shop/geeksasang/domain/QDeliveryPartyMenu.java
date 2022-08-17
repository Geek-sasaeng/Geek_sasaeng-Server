package shop.geeksasang.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeliveryPartyMenu is a Querydsl query type for DeliveryPartyMenu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryPartyMenu extends EntityPathBase<DeliveryPartyMenu> {

    private static final long serialVersionUID = 1049234308L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeliveryPartyMenu deliveryPartyMenu = new QDeliveryPartyMenu("deliveryPartyMenu");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QDeliveryPartyMember menuOwner;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status = _super.status;

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QDeliveryPartyMenu(String variable) {
        this(DeliveryPartyMenu.class, forVariable(variable), INITS);
    }

    public QDeliveryPartyMenu(Path<? extends DeliveryPartyMenu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeliveryPartyMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeliveryPartyMenu(PathMetadata metadata, PathInits inits) {
        this(DeliveryPartyMenu.class, metadata, inits);
    }

    public QDeliveryPartyMenu(Class<? extends DeliveryPartyMenu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.menuOwner = inits.isInitialized("menuOwner") ? new QDeliveryPartyMember(forProperty("menuOwner"), inits.get("menuOwner")) : null;
    }

}

