package momentlockdemo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import momentlockdemo.entity.Inquiry;
import momentlockdemo.entity.Member;

public interface InquiryService {

	// 기본 CRUD
	public abstract Inquiry createInquiry(Inquiry inquiry);

	public abstract Optional<Inquiry> getInquiryById(Long inqid);

	public abstract List<Inquiry> getAllInquiries();

	public abstract Page<Inquiry> getAllInquiries(Pageable pageable);

	public abstract Inquiry updateInquiry(Inquiry inquiry);

	public abstract void deleteInquiry(Long inqid);

	// 추가 비즈니스 로직
	public abstract List<Inquiry> getInquiriesByMember(Member member);

	public abstract List<Inquiry> getInquiriesByMemberOrderByDateDesc(Member member);

	public abstract List<Inquiry> getInquiriesByComplete(String inqcomplete);

	public abstract List<Inquiry> searchInquiriesByTitle(String inqtitle);

	public abstract List<Inquiry> getInquiriesByDateRange(LocalDateTime startDate, LocalDateTime endDate);

	public abstract long countInquiriesByComplete(String inqcomplete);

}
