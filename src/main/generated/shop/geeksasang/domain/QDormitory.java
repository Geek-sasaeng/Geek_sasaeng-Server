package shop.geeksasang.domain;

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

    private static final long serialVersionUID = -1527450536L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDormitory dormitory = new QDormitory("dormitory");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final ListPath<DeliveryParty, QDeliveryParty> deliveryParties = this.<DeliveryParty, QDeliveryParty>createList("deliveryParties", DeliveryParty.class, QDeliveryParty.class, PathInits.DIRECT2);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QLocation location;

    public final ListPath<Member, QMember> members = this.<Member, QMember>createList("members", Member.class, QMember.class, PathInits.DIRECT2);

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
        this.location = inits.isInitialized("location") ? new QLocation(forProperty("location")) : null;
        this.university = inits.isInitialized("university") ? new QUniversity(forProperty("university")) : null;
    }

}

