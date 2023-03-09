package shop.geeksasang.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.member.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    @Query("select m from Member m where m.loginId = :loginId and m.status = 'ACTIVE' ")
    Optional<Member> findMemberByLoginId(String loginId);

    @Query("select m from Member m where m.email.id = :emailId and m.status = 'ACTIVE' ")
    Optional<Member> findMemberByEmailId(int emailId);

    @Query("select m from Member m where m.nickName = :nickName and m.status = 'ACTIVE' ")
    Optional<Member> findMemberByNickName(String nickName);

    @Query("select m from Member m where m.id = :id and m.status = 'ACTIVE' ")
    Optional<Member> findMemberById(int id);

    @Query("select m from Member m where m.id = :id and m.status = 'ACTIVE' ")
    Optional<Member> findMemberByIdAndStatus(int id);


    @Modifying
    @Query("update Member m set m.perDayReportingCount = 0")
    int bulkDayReportingCountInit();

    boolean existsByLoginId(String loginId);

    @Query("select u from Member u where u.appleRefreshToken = :refreshToken")
    Optional<Member> findByAppleRefreshToken(@Param("refreshToken") String refreshToken);
}
