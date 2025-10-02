package momentlockdemo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import momentlockdemo.entity.Inquiry;
import momentlockdemo.entity.Member;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

	List<Inquiry> findByMember(Member member);

	List<Inquiry> findByMemberOrderByInqregdateDesc(Member member);

	List<Inquiry> findByInqcomplete(String inqcomplete);

	List<Inquiry> findByInqtitleContaining(String inqtitle);

	List<Inquiry> findByInqregdateBetween(LocalDateTime startDate, LocalDateTime endDate);

	long countByInqcomplete(String inqcomplete);
}
