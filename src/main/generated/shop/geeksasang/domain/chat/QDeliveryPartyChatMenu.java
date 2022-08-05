package shop.geeksasang.domain.chat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeliveryPartyChatMenu is a Querydsl query type for DeliveryPartyChatMenu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryPartyChatMenu extends EntityPathBase<DeliveryPartyChatMenu> {

    private static final long serialVersionUID = 875015584L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeliveryPartyChatMenu deliveryPartyChatMenu = new QDeliveryPartyChatMenu("deliveryPartyChatMenu");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final QDeliveryPartyChatRoom deliveryPartyChatRoom;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status = _super.status;

    public final StringPath title = createString("title");

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QDeliveryPartyChatMenu(String variable) {
        this(DeliveryPartyChatMenu.class, forVariable(variable), INITS);
    }

    public QDeliveryPartyChatMenu(Path<? extends DeliveryPartyChatMenu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeliveryPartyChatMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeliveryPartyChatMenu(PathMetadata metadata, PathInits inits) {
        this(DeliveryPartyChatMenu.class, metadata, inits);
    }

    public QDeliveryPartyChatMenu(Class<? extends DeliveryPartyChatMenu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deliveryPartyChatRoom = inits.isInitialized("deliveryPartyChatRoom") ? new QDeliveryPartyChatRoom(forProperty("deliveryPartyChatRoom"), inits.get("deliveryPartyChatRoom")) : null;
    }

}

