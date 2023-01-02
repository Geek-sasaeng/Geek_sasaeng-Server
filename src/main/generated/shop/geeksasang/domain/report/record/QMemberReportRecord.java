package shop.geeksasang.domain.report.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberReportRecord is a Querydsl query type for MemberReportRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberReportRecord extends EntityPathBase<MemberReportRecord> {

    private static final long serialVersionUID = 1615923919L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberReportRecord memberReportRecord = new QMemberReportRecord("memberReportRecord");

    public final QReportRecord _super;

    //inherited
    public final StringPath createdAt;

    //inherited
    public final NumberPath<Integer> id;

    // inherited
    public final shop.geeksasang.domain.member.QMember owner;

    public final shop.geeksasang.domain.member.QMember reportedMember;

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status;

    //inherited
    public final StringPath updatedAt;

    public QMemberReportRecord(String variable) {
        this(MemberReportRecord.class, forVariable(variable), INITS);
    }

    public QMemberReportRecord(Path<? extends MemberReportRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberReportRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberReportRecord(PathMetadata metadata, PathInits inits) {
        this(MemberReportRecord.class, metadata, inits);
    }

    public QMemberReportRecord(Class<? extends MemberReportRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QReportRecord(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.owner = _super.owner;
        this.reportedMember = inits.isInitialized("reportedMember") ? new shop.geeksasang.domain.member.QMember(forProperty("reportedMember"), inits.get("reportedMember")) : null;
        this.status = _super.status;
        this.updatedAt = _super.updatedAt;
    }

}

