package shop.geeksasang.domain.chat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeliveryPartyChatRoom is a Querydsl query type for DeliveryPartyChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryPartyChatRoom extends EntityPathBase<DeliveryPartyChatRoom> {

    private static final long serialVersionUID = 875174172L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeliveryPartyChatRoom deliveryPartyChatRoom = new QDeliveryPartyChatRoom("deliveryPartyChatRoom");

    public final QChatRoom _super = new QChatRoom(this);

    public final StringPath account = createString("account");

    //inherited
    public final ListPath<Chat, QChat> chats = _super.chats;

    public final shop.geeksasang.domain.QDeliveryParty deliveryParty;

    public final NumberPath<Integer> deliveryTip = createNumber("deliveryTip", Integer.class);

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final EnumPath<shop.geeksasang.config.status.OrderStatus> orderStatus = createEnum("orderStatus", shop.geeksasang.config.status.OrderStatus.class);

    //inherited
    public final StringPath title = _super.title;

    public QDeliveryPartyChatRoom(String variable) {
        this(DeliveryPartyChatRoom.class, forVariable(variable), INITS);
    }

    public QDeliveryPartyChatRoom(Path<? extends DeliveryPartyChatRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeliveryPartyChatRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeliveryPartyChatRoom(PathMetadata metadata, PathInits inits) {
        this(DeliveryPartyChatRoom.class, metadata, inits);
    }

    public QDeliveryPartyChatRoom(Class<? extends DeliveryPartyChatRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deliveryParty = inits.isInitialized("deliveryParty") ? new shop.geeksasang.domain.QDeliveryParty(forProperty("deliveryParty"), inits.get("deliveryParty")) : null;
    }

}

