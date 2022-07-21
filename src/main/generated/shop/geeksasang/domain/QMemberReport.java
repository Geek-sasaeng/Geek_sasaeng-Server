package shop.geeksasang.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberReport is a Querydsl query type for MemberReport
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberReport extends EntityPathBase<MemberReport> {

    private static final long serialVersionUID = 1241979099L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberReport memberReport = new QMemberReport("memberReport");

    public final QReport _super;

    //inherited
    public final StringPath content;

    //inherited
    public final StringPath createdAt;

    //inherited
    public final NumberPath<Integer> id;

    // inherited
    public final QReportCategory reportCategory;

    // inherited
    public final QMember reportedMember;

    // inherited
    public final QMember reportingMember;

    //inherited
    public final EnumPath<shop.geeksasang.config.status.ReportStatus> reportStatus;

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status;

    //inherited
    public final StringPath updatedAt;

    public QMemberReport(String variable) {
        this(MemberReport.class, forVariable(variable), INITS);
    }

    public QMemberReport(Path<? extends MemberReport> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberReport(PathMetadata metadata, PathInits inits) {
        this(MemberReport.class, metadata, inits);
    }

    public QMemberReport(Class<? extends MemberReport> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QReport(type, metadata, inits);
        this.content = _super.content;
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.reportCategory = _super.reportCategory;
        this.reportedMember = _super.reportedMember;
        this.reportingMember = _super.reportingMember;
        this.reportStatus = _super.reportStatus;
        this.status = _super.status;
        this.updatedAt = _super.updatedAt;
    }

}

