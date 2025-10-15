package momentlockdemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Member;
import momentlockdemo.entity.MemberBox;
import momentlockdemo.entity.MemberBoxId;

public interface MemberBoxRepository extends JpaRepository<MemberBox, MemberBoxId> {

	List<MemberBox> findByMember(Member member);

	List<MemberBox> findByBox(Box box);

	boolean existsByMemberAndBox(Member member, Box box);

	Optional<MemberBox> findByMemberAndBox(Member member, Box box);
	
	Optional<MemberBox> getMemberBoxByBox(Box box);

	List<MemberBox> findByReadycode(String readycode);

	long countByBox(Box box);

	@Query("SELECT mb FROM MemberBox mb WHERE mb.box = :box AND mb.boxmatercode = :matercode")
	List<MemberBox> findBoxManagers(@Param("box") Box box, @Param("matercode") String matercode);

    // MCB를 먼저 정렬하는 쿼리
    @Query("SELECT mb FROM MemberBox mb " +
           "LEFT JOIN FETCH mb.member " +
           "WHERE mb.box = :box " +
           "ORDER BY CASE WHEN mb.boxmatercode = 'MCB' THEN 0 ELSE 1 END, mb.member.username")
    List<MemberBox> findByBoxOrderByBoxmatercodeDesc(@Param("box") Box box);
    
}





