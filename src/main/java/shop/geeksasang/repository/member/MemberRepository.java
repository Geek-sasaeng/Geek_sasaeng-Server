package shop.geeksasang.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.member.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findMemberByLoginId(String loginId);

    Optional<Member> findMemberByEmailId(int emailId);

    Optional<Member> findMemberByNickName(String nickName);

    Optional<Member> findMemberById(int id);

    @Query("select m from Member m where m.id = :id and m.status = 'ACTIVE' ")
    Optional<Member> findMemberByIdAndStatus(int id);


    @Modifying
    @Query("update Member m set m.perDayReportingCount = 0")
    int bulkDayReportingCountInit();

    Optional<Member> findMemberByPhoneNumberId(int phoneNumberId);

    @Query("select m from Member m left join DeliveryPartyMember dpm on dpm.participant.id = m.id where dpm.party.id = :partyId and not m.id = :userId and m.status = 'ACTIVE'")
    List<Member> findMemberFcmTockenById(int partyId, int userId);
}
