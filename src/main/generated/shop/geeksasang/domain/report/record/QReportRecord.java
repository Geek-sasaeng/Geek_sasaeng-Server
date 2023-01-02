package shop.geeksasang.domain.report.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReportRecord is a Querydsl query type for ReportRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReportRecord extends EntityPathBase<ReportRecord> {

    private static final long serialVersionUID = -1694607595L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReportRecord reportRecord = new QReportRecord("reportRecord");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final shop.geeksasang.domain.member.QMember owner;

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status = _super.status;

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QReportRecord(String variable) {
        this(ReportRecord.class, forVariable(variable), INITS);
    }

    public QReportRecord(Path<? extends ReportRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReportRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReportRecord(PathMetadata metadata, PathInits inits) {
        this(ReportRecord.class, metadata, inits);
    }

    public QReportRecord(Class<? extends ReportRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.owner = inits.isInitialized("owner") ? new shop.geeksasang.domain.member.QMember(forProperty("owner"), inits.get("owner")) : null;
    }

}

