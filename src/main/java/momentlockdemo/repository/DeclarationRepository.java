package momentlockdemo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Declaration;
import momentlockdemo.entity.Member;

public interface DeclarationRepository extends JpaRepository<Declaration, Long> {

	List<Declaration> findByMember(Member member);

	List<Declaration> findByCapsule(Capsule capsule);

	List<Declaration> findByDeccategory(String deccategory);

	List<Declaration> findByDeccomplete(String deccomplete);

	List<Declaration> findByDecregdateBetween(LocalDateTime startDate, LocalDateTime endDate);

	long countByDeccomplete(String deccomplete);
}
