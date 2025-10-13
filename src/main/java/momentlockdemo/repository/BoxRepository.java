package momentlockdemo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import momentlockdemo.dto.BoxLikeCountDto;
import momentlockdemo.entity.Box;

public interface BoxRepository extends JpaRepository<Box, Long> {

	List<Box> findByBoxnameContaining(String boxname);

	Optional<Box> findByBoxinvitecode(String boxinvitecode);

	List<Box> findByBoxlocationContaining(String boxlocation);

	List<Box> findByBoxopendateBefore(LocalDateTime date);

	List<Box> findByBoxopendateAfter(LocalDateTime date);

	Optional<Box> findByBoxburycode(String boxburycode);

//	이 쿼리는 묻히지 않은, 캡슐이 하나 이상 들어있는 박스와 
//	각각의 박스 별 캡슐들이 받은 좋아요 수의 합을 
//	좋아요 내림차 순으로 한꺼번에 가져오는 select문입니다. 
	@Query("SELECT b.boxid, b.boxcapcount, b.boxmemcount, b.boxopendate, b.boxregdate, "
			+ "b.latitude, b.longitude, b.boxlocation, b.boxname, SUM(c.caplikecount) "
			+ "FROM Capsule c JOIN c.box b WHERE b.boxburycode = 'BBN' AND b.boxdelcode = 'BDN' "
			+ "AND b.boxreleasecode = 'B00' AND b.boxcapcount >= 1 "
			+ "GROUP BY b.boxid, b.boxcapcount, b.boxmemcount, b.boxopendate, b.boxregdate, "
			+ "b.latitude, b.longitude, b.boxlocation, b.boxname ORDER BY SUM(c.caplikecount) DESC")
	Page<BoxLikeCountDto> getPopularBoxPage(Pageable pageable);
	// Pageable을 인자로 주면 Page 객체를 리턴해주는 기본 JpaRepository 메소드
	
//	opensoonbox.html에서 사용할 Page 객체
	Page<Box> findAll(Pageable pageable);
	
}
