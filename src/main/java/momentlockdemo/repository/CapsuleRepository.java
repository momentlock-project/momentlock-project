package momentlockdemo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import momentlockdemo.entity.Box;
import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Member;

@Repository
public interface CapsuleRepository extends JpaRepository<Capsule, Long> {

	List<Capsule> findByBox(Box box);

	List<Capsule> findByBoxOrderByCapregdateDesc(Box box);

	List<Capsule> findByMember(Member member);

	List<Capsule> findByCaptitleContaining(String captitle);

	List<Capsule> findByBoxAndCaptitleContaining(Box box, String captitle);

	List<Capsule> findByCapregdateBetween(LocalDateTime startDate, LocalDateTime endDate);

	long countByBox(Box box);

	long countByMember(Member member);

	// 좋아요 관련
	List<Capsule> findAllByOrderByCaplikecountDesc();
	
    // 좋아요 수가 많은 상위 N개 캡슐 조회 (인기 캡슐)
    List<Capsule> findTop10ByOrderByCaplikecountDesc();
}
