package momentlockdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import momentlockdemo.entity.EmailAuthCode;

@Repository("emailAuthCodeRepository")
public interface EmailAuthCodeRepository extends JpaRepository<EmailAuthCode, Long>{
	
	Optional<EmailAuthCode> findTopByEmailOrderByCreatedAtDesc(String email);

    Optional<EmailAuthCode> findByEmailAndCodeAndUsedIsFalse(String email, String code);

    void deleteByEmail(String email);

}
