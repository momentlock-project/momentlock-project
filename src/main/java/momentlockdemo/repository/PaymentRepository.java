package momentlockdemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import momentlockdemo.entity.Member;
import momentlockdemo.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> findByMember(Member member);

	List<Payment> findByMemberOrderByPayidDesc(Member member);

	Optional<Payment> findByPaynumber(Long paynumber);

	Optional<Payment> findByPaycode(String paycode);
}
