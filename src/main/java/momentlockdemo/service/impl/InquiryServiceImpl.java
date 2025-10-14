package momentlockdemo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Inquiry;
import momentlockdemo.entity.Member;
import momentlockdemo.repository.InquiryRepository;
import momentlockdemo.service.InquiryService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InquiryServiceImpl implements InquiryService {
    
    private final InquiryRepository inquiryRepository;
    
    @Override
    @Transactional
    public Inquiry createInquiry(Inquiry inquiry) {
        return inquiryRepository.save(inquiry);
    }
    
    @Override
    public Optional<Inquiry> getInquiryById(Long inqid) {
        return inquiryRepository.findById(inqid);
    }
    
    @Override
    public Page<Inquiry> getAllInquiries(Pageable pageable) {
        return inquiryRepository.findAll(pageable);
    }
    
    @Override
    @Transactional
    public Inquiry updateInquiry(Inquiry inquiry) {
        return inquiryRepository.save(inquiry);
    }
    
    @Override
    @Transactional
    public void deleteInquiry(Long inqid) {
        inquiryRepository.deleteById(inqid);
    }
    
    @Override
    public List<Inquiry> getInquiriesByMember(Member member) {
        return inquiryRepository.findByMember(member);
    }
    
    @Override
    public List<Inquiry> getInquiriesByMemberOrderByDateDesc(Member member) {
        return inquiryRepository.findByMemberOrderByInqregdateDesc(member);
    }
    
    @Override
    public List<Inquiry> getInquiriesByComplete(String inqcomplete) {
        return inquiryRepository.findByInqcomplete(inqcomplete);
    }
    
    @Override
    public List<Inquiry> searchInquiriesByTitle(String inqtitle) {
        return inquiryRepository.findByInqtitleContaining(inqtitle);
    }
    
    @Override
    public List<Inquiry> getInquiriesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return inquiryRepository.findByInqregdateBetween(startDate, endDate);
    }
    
    @Override
    public long countInquiriesByComplete(String inqcomplete) {
        return inquiryRepository.countByInqcomplete(inqcomplete);
    }
}
