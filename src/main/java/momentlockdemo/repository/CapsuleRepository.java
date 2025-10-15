package momentlockdemo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
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
    
 // member를 JOIN FETCH 해서 한 번에 가져오기
    @Query("SELECT c FROM Capsule c JOIN FETCH c.member")
    List<Capsule> findAllWithMember();
    
    // 페이징용 (Page 반환)
    @Query(value = "SELECT c FROM Capsule c JOIN FETCH c.member",
           countQuery = "SELECT COUNT(c) FROM Capsule c")
    Page<Capsule> findAllWithMember(Pageable pageable);
    
//  좋아요 수 증가 or 감소 후 해당 좋아요 수 반환 함수 호출 쿼리
//  반환타입을 int로 해도 되지만 Integer가 null에 관대해서 Integer로 함
    @Query(value = "SELECT FUNC_CAP_LIKECOUNT_UPDATE(:capid, :action) FROM DUAL COMMIT", nativeQuery = true)
    Integer casuleLikeCountIncrease(@Param(value = "capid") Long capid, @Param(value = "action") String action);
    
}
