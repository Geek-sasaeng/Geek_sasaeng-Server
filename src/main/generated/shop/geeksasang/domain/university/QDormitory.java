package shop.geeksasang.domain.university;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDormitory is a Querydsl query type for Dormitory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDormitory extends EntityPathBase<Dormitory> {

    private static final long serialVersionUID = -1419265678L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDormitory dormitory = new QDormitory("dormitory");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final ListPath<shop.geeksasang.domain.deliveryparty.DeliveryParty, shop.geeksasang.domain.deliveryparty.QDeliveryParty> deliveryParties = this.<shop.geeksasang.domain.deliveryparty.DeliveryParty, shop.geeksasang.domain.deliveryparty.QDeliveryParty>createList("deliveryParties", shop.geeksasang.domain.deliveryparty.DeliveryParty.class, shop.geeksasang.domain.deliveryparty.QDeliveryParty.class, PathInits.DIRECT2);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final shop.geeksasang.domain.location.QLocation location;

    public final ListPath<shop.geeksasang.domain.member.Member, shop.geeksasang.domain.member.QMember> members = this.<shop.geeksasang.domain.member.Member, shop.geeksasang.domain.member.QMember>createList("members", shop.geeksasang.domain.member.Member.class, shop.geeksasang.domain.member.QMember.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status = _super.status;

    public final QUniversity university;

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QDormitory(String variable) {
        this(Dormitory.class, forVariable(variable), INITS);
    }

    public QDormitory(Path<? extends Dormitory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDormitory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDormitory(PathMetadata metadata, PathInits inits) {
        this(Dormitory.class, metadata, inits);
    }

    public QDormitory(Class<? extends Dormitory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.location = inits.isInitialized("location") ? new shop.geeksasang.domain.location.QLocation(forProperty("location")) : null;
        this.university = inits.isInitialized("university") ? new QUniversity(forProperty("university")) : null;
    }

}

