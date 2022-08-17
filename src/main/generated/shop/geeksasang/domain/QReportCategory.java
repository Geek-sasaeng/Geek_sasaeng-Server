package shop.geeksasang.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReportCategory is a Querydsl query type for ReportCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReportCategory extends EntityPathBase<ReportCategory> {

    private static final long serialVersionUID = -1587166337L;

    public static final QReportCategory reportCategory = new QReportCategory("reportCategory");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Integer> reportCategoryId = createNumber("reportCategoryId", Integer.class);

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status = _super.status;

    public final StringPath title = createString("title");

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QReportCategory(String variable) {
        super(ReportCategory.class, forVariable(variable));
    }

    public QReportCategory(Path<? extends ReportCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReportCategory(PathMetadata metadata) {
        super(ReportCategory.class, metadata);
    }

}

