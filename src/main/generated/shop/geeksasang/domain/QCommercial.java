package shop.geeksasang.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommercial is a Querydsl query type for Commercial
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommercial extends EntityPathBase<Commercial> {

    private static final long serialVersionUID = -150660361L;

    public static final QCommercial commercial = new QCommercial("commercial");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath imgUrl = createString("imgUrl");

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status = _super.status;

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QCommercial(String variable) {
        super(Commercial.class, forVariable(variable));
    }

    public QCommercial(Path<? extends Commercial> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommercial(PathMetadata metadata) {
        super(Commercial.class, metadata);
    }

}

