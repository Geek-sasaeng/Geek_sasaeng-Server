package shop.geeksasang.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 759956961L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    public final ListPath<shop.geeksasang.domain.block.Block, shop.geeksasang.domain.block.QBlock> blocks = this.<shop.geeksasang.domain.block.Block, shop.geeksasang.domain.block.QBlock>createList("blocks", shop.geeksasang.domain.block.Block.class, shop.geeksasang.domain.block.QBlock.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final ListPath<shop.geeksasang.domain.report.record.DeliverPartyReportRecord, shop.geeksasang.domain.report.record.QDeliverPartyReportRecord> deliverPartyReportRecords = this.<shop.geeksasang.domain.report.record.DeliverPartyReportRecord, shop.geeksasang.domain.report.record.QDeliverPartyReportRecord>createList("deliverPartyReportRecords", shop.geeksasang.domain.report.record.DeliverPartyReportRecord.class, shop.geeksasang.domain.report.record.QDeliverPartyReportRecord.class, PathInits.DIRECT2);

    public final shop.geeksasang.domain.university.QDormitory dormitory;

    public final shop.geeksasang.domain.auth.QEmail email;

    public final StringPath fcmToken = createString("fcmToken");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath informationAgreeStatus = createString("informationAgreeStatus");

    public final StringPath jwtToken = createString("jwtToken");

    public final StringPath loginId = createString("loginId");

    public final EnumPath<shop.geeksasang.config.status.LoginStatus> loginStatus = createEnum("loginStatus", shop.geeksasang.config.status.LoginStatus.class);

    public final EnumPath<shop.geeksasang.config.type.MemberLoginType> memberLoginType = createEnum("memberLoginType", shop.geeksasang.config.type.MemberLoginType.class);

    public final ListPath<shop.geeksasang.domain.report.record.MemberReportRecord, shop.geeksasang.domain.report.record.QMemberReportRecord> memberReportRecords = this.<shop.geeksasang.domain.report.record.MemberReportRecord, shop.geeksasang.domain.report.record.QMemberReportRecord>createList("memberReportRecords", shop.geeksasang.domain.report.record.MemberReportRecord.class, shop.geeksasang.domain.report.record.QMemberReportRecord.class, PathInits.DIRECT2);

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final NumberPath<Integer> perDayReportingCount = createNumber("perDayReportingCount", Integer.class);

    public final shop.geeksasang.domain.auth.QPhoneNumber phoneNumber;

    public final StringPath profileImgUrl = createString("profileImgUrl");

    public final NumberPath<Integer> reportedCount = createNumber("reportedCount", Integer.class);

    public final ListPath<shop.geeksasang.domain.report.MemberReport, shop.geeksasang.domain.report.QMemberReport> reportedMembers = this.<shop.geeksasang.domain.report.MemberReport, shop.geeksasang.domain.report.QMemberReport>createList("reportedMembers", shop.geeksasang.domain.report.MemberReport.class, shop.geeksasang.domain.report.QMemberReport.class, PathInits.DIRECT2);

    public final ListPath<shop.geeksasang.domain.report.MemberReport, shop.geeksasang.domain.report.QMemberReport> reportingMembers = this.<shop.geeksasang.domain.report.MemberReport, shop.geeksasang.domain.report.QMemberReport>createList("reportingMembers", shop.geeksasang.domain.report.MemberReport.class, shop.geeksasang.domain.report.QMemberReport.class, PathInits.DIRECT2);

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status = _super.status;

    public final shop.geeksasang.domain.university.QUniversity university;

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dormitory = inits.isInitialized("dormitory") ? new shop.geeksasang.domain.university.QDormitory(forProperty("dormitory"), inits.get("dormitory")) : null;
        this.email = inits.isInitialized("email") ? new shop.geeksasang.domain.auth.QEmail(forProperty("email"), inits.get("email")) : null;
        this.phoneNumber = inits.isInitialized("phoneNumber") ? new shop.geeksasang.domain.auth.QPhoneNumber(forProperty("phoneNumber"), inits.get("phoneNumber")) : null;
        this.university = inits.isInitialized("university") ? new shop.geeksasang.domain.university.QUniversity(forProperty("university")) : null;
    }

}

