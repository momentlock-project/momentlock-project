package momentlockdemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import momentlockdemo.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

	Optional<Member> findByNameAndPhonenumber(String name, String phonenumber);
	
	Optional<Member> findByUsername(String username);
	
	Optional<Member> findByNickname(String nickname);

	Optional<Member> findByPhonenumber(String phonenumber);

	Optional<Member> findByMemcode(String memcode);
	
	boolean existsByUsername(String username);

	boolean existsByNickname(String nickname);

	boolean existsByPhonenumber(String phonenumber);
   
    List<Member> findAllByOrderByMemdeccountDesc();
    
    Page<Member> findByNicknameContainingIgnoreCase(String nickname, Pageable pageable);
    
}
