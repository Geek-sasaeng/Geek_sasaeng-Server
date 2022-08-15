package shop.geeksasang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.Announcement;

import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

    @Query("select a from Announcement a where a.id = :announcementId and a.status = 'ACTIVE'")
    Optional<Announcement> findByIdAndStatus(@Param("announcementId") int announcementId);
}
