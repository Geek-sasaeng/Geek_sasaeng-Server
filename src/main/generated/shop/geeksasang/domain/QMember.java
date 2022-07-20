package shop.geeksasang.domain;

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

    private static final long serialVersionUID = 1163118791L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final shop.geeksasang.config.domain.QBaseEntity _super = new shop.geeksasang.config.domain.QBaseEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final QEmail email;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath informationAgreeStatus = createString("informationAgreeStatus");

    public final StringPath jwtToken = createString("jwtToken");

    public final StringPath loginId = createString("loginId");

    public final EnumPath<shop.geeksasang.config.status.LoginStatus> loginStatus = createEnum("loginStatus", shop.geeksasang.config.status.LoginStatus.class);

    public final EnumPath<shop.geeksasang.config.type.MemberLoginType> memberLoginType = createEnum("memberLoginType", shop.geeksasang.config.type.MemberLoginType.class);

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final QPhoneNumber phoneNumber;

    public final StringPath profileImgUrl = createString("profileImgUrl");

    public final ListPath<MemberReport, QMemberReport> reportedMembers = this.<MemberReport, QMemberReport>createList("reportedMembers", MemberReport.class, QMemberReport.class, PathInits.DIRECT2);

    public final ListPath<MemberReport, QMemberReport> reportingMembers = this.<MemberReport, QMemberReport>createList("reportingMembers", MemberReport.class, QMemberReport.class, PathInits.DIRECT2);

    //inherited
    public final EnumPath<shop.geeksasang.config.status.BaseStatus> status = _super.status;

    public final QUniversity university;

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
        this.email = inits.isInitialized("email") ? new QEmail(forProperty("email"), inits.get("email")) : null;
        this.phoneNumber = inits.isInitialized("phoneNumber") ? new QPhoneNumber(forProperty("phoneNumber"), inits.get("phoneNumber")) : null;
        this.university = inits.isInitialized("university") ? new QUniversity(forProperty("university")) : null;
    }

}

