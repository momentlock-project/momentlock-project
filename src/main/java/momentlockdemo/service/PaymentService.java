package momentlockdemo.service;

import java.util.List;
import java.util.Optional;

import momentlockdemo.entity.Member;
import momentlockdemo.entity.Payment;

public interface PaymentService {

	// 기본 CRUD
	public abstract Payment createPayment(Payment payment);

	public abstract Optional<Payment> getPaymentById(Long payid);

	public abstract List<Payment> getAllPayments();

	public abstract Payment updatePayment(Payment payment);

	public abstract void deletePayment(Long payid);

	// 추가 비즈니스 로직
	public abstract List<Payment> getPaymentsByMember(Member member);

	public abstract List<Payment> getPaymentsByMemberOrderByIdDesc(Member member);

	public abstract Optional<Payment> getPaymentByPaynumber(Long paynumber);

	public abstract Optional<Payment> getPaymentByPaycode(String paycode);
}
