package shop.geeksasang.domain.auth;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhoneNumber is a Querydsl query type for PhoneNumber
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhoneNumber extends EntityPathBase<PhoneNumber> {

    private static final long serialVersionUID = -1953783618L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPhoneNumber phoneNumber = new QPhoneNumber("phoneNumber");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final shop.geeksasang.domain.member.QMember member;

    public final StringPath number = createString("number");

    public final EnumPath<shop.geeksasang.config.status.ValidStatus> phoneValidStatus = createEnum("phoneValidStatus", shop.geeksasang.config.status.ValidStatus.class);

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status = _super.status;

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QPhoneNumber(String variable) {
        this(PhoneNumber.class, forVariable(variable), INITS);
    }

    public QPhoneNumber(Path<? extends PhoneNumber> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPhoneNumber(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPhoneNumber(PathMetadata metadata, PathInits inits) {
        this(PhoneNumber.class, metadata, inits);
    }

    public QPhoneNumber(Class<? extends PhoneNumber> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new shop.geeksasang.domain.member.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

