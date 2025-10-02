package momentlockdemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import momentlockdemo.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

	Optional<Member> findByNickname(String nickname);

	Optional<Member> findByPhonenumber(String phonenumber);

	Optional<Member> findByMemcode(String memcode);

	boolean existsByNickname(String nickname);

	boolean existsByPhonenumber(String phonenumber);
   
    List<Member> findAllByOrderByMemdeccountDesc();
    
}
