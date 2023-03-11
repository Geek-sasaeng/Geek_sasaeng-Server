package shop.geeksasang.domain.auth;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVerificationCount is a Querydsl query type for VerificationCount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVerificationCount extends EntityPathBase<VerificationCount> {

    private static final long serialVersionUID = -2102697765L;

    public static final QVerificationCount verificationCount = new QVerificationCount("verificationCount");

    public final NumberPath<Integer> emailVerificationCount = createNumber("emailVerificationCount", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> smsVerificationCount = createNumber("smsVerificationCount", Integer.class);

    public final StringPath UUID = createString("UUID");

    public QVerificationCount(String variable) {
        super(VerificationCount.class, forVariable(variable));
    }

    public QVerificationCount(Path<? extends VerificationCount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVerificationCount(PathMetadata metadata) {
        super(VerificationCount.class, metadata);
    }

}

