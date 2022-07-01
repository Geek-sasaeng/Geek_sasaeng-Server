package shop.geeksasang.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.Domitory;

@Repository
public interface DomitoryRepository extends JpaRepository<Domitory, Integer> {
}
