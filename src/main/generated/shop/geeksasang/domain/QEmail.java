package shop.geeksasang.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmail is a Querydsl query type for Email
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmail extends EntityPathBase<Email> {

    private static final long serialVersionUID = 1831474127L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmail email = new QEmail("email");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    public final StringPath address = createString("address");

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final EnumPath<shop.geeksasang.config.status.ValidStatus> emailValidStatus = createEnum("emailValidStatus", shop.geeksasang.config.status.ValidStatus.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QMember member;

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status = _super.status;

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QEmail(String variable) {
        this(Email.class, forVariable(variable), INITS);
    }

    public QEmail(Path<? extends Email> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmail(PathMetadata metadata, PathInits inits) {
        this(Email.class, metadata, inits);
    }

    public QEmail(Class<? extends Email> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

