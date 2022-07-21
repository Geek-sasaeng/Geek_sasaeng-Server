package shop.geeksasang.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUniversity is a Querydsl query type for University
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUniversity extends EntityPathBase<University> {

    private static final long serialVersionUID = -863679941L;

    public static final QUniversity university = new QUniversity("university");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final ListPath<Dormitory, QDormitory> dormitories = this.<Dormitory, QDormitory>createList("dormitories", Dormitory.class, QDormitory.class, PathInits.DIRECT2);

    public final StringPath emailAddress = createString("emailAddress");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status = _super.status;

    public final StringPath universityImgUrl = createString("universityImgUrl");

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QUniversity(String variable) {
        super(University.class, forVariable(variable));
    }

    public QUniversity(Path<? extends University> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUniversity(PathMetadata metadata) {
        super(University.class, metadata);
    }

}

