package momentlockdemo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Member;
import momentlockdemo.repository.CapsuleRepository;
import momentlockdemo.service.CapsuleService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CapsuleServiceImpl implements CapsuleService {
    
    private final CapsuleRepository capsuleRepository;
    
    
    @Override
    @Transactional
    public Capsule createCapsule(Capsule capsule) {
        return capsuleRepository.save(capsule);
    }
    
    @Override
    public Optional<Capsule> getCapsuleById(Long capid) {
        return capsuleRepository.findById(capid);
    }
    
    @Override
    public List<Capsule> getAllCapsules() {
        return capsuleRepository.findAll();
    }
    
    @Override
    @Transactional
    public Capsule insertCapsule(Capsule capsule) {
       return capsuleRepository.save(capsule);
    }
    
    
    @Override
    @Transactional
    public Capsule updateCapsule(Capsule capsule) {
        return capsuleRepository.save(capsule);
    }
    
    @Override
    @Transactional
    public void deleteCapsule(Long capid) {
        capsuleRepository.deleteById(capid);
    }
    
    @Override
    public List<Capsule> getCapsulesByBox(Box box) {
        return capsuleRepository.findByBox(box);
    }
    
    @Override
    public List<Capsule> getCapsulesByBoxOrderByDateDesc(Box box) {
        return capsuleRepository.findByBoxOrderByCapregdateDesc(box);
    }
    
    @Override
    public List<Capsule> getCapsulesByMember(Member member) {
        return capsuleRepository.findByMember(member);
    }
    
    @Override
    public List<Capsule> searchCapsulesByTitle(String captitle) {
        return capsuleRepository.findByCaptitleContaining(captitle);
    }
    
    @Override
    public List<Capsule> searchCapsulesByBoxAndTitle(Box box, String captitle) {
        return capsuleRepository.findByBoxAndCaptitleContaining(box, captitle);
    }
    
    @Override
    public List<Capsule> getCapsulesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return capsuleRepository.findByCapregdateBetween(startDate, endDate);
    }
    
    @Override
    public long countCapsulesByBox(Box box) {
        return capsuleRepository.countByBox(box);
    }
    
    @Override
    public long countCapsulesByMember(Member member) {
        return capsuleRepository.countByMember(member);
    }
    
    @Override
    public List<Capsule> getCapsulesOrderByLikeDesc() {
       return capsuleRepository.findAllByOrderByCaplikecountDesc();
    }
    
    @Override
    public List<Capsule> getCapsulesTopLike() {
       return capsuleRepository.findTop10ByOrderByCaplikecountDesc();
    }
    
}
