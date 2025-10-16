package momentlockdemo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Member;
import momentlockdemo.entity.Payment;
import momentlockdemo.repository.PaymentRepository;
import momentlockdemo.service.PaymentService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {
    
    private final PaymentRepository paymentRepository;
    
    @Override
    @Transactional
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
    
    @Override
    public Optional<Payment> getPaymentById(Long payid) {
        return paymentRepository.findById(payid);
    }
    
    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    
    @Override
    @Transactional
    public Payment updatePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
    
    @Override
    @Transactional
    public void deletePayment(Long payid) {
        paymentRepository.deleteById(payid);
    }
    
    @Override
    public List<Payment> getPaymentsByMember(Member member) {
        return paymentRepository.findByMember(member);
    }
    
    @Override
    public List<Payment> getPaymentsByMemberOrderByIdDesc(Member member) {
        return paymentRepository.findByMemberOrderByPayidDesc(member);
    }
    
    @Override
    public Optional<Payment> getPaymentByPaynumber(Long paynumber) {
        return paymentRepository.findByPaynumber(paynumber);
    }
    
    @Override
    public Optional<Payment> getPaymentByPaycode(String paycode) {
        return paymentRepository.findByPaycode(paycode);
    }
    
    @Override
    public Page<Payment> getAllPaymentPage(Pageable pageable) {
    	return paymentRepository.findAll(pageable);
    }
}
