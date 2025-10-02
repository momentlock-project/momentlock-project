package momentlockdemo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Afile;
import momentlockdemo.entity.Capsule;
import momentlockdemo.repository.AfileRepository;
import momentlockdemo.service.AfileService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AfileServiceImpl implements AfileService {
    
    private final AfileRepository afileRepository;
    
    @Override
    @Transactional
    public Afile createAfile(Afile afile) {
        return afileRepository.save(afile);
    }
    
    @Override
    public Optional<Afile> getAfileById(Long afid) {
        return afileRepository.findById(afid);
    }
    
    @Override
    public List<Afile> getAllAfiles() {
        return afileRepository.findAll();
    }
    
    @Override
    @Transactional
    public Afile updateAfile(Afile afile) {
        return afileRepository.save(afile);
    }
    
    @Override
    @Transactional
    public void deleteAfile(Long afid) {
        afileRepository.deleteById(afid);
    }
    
    @Override
    public List<Afile> getAfilesByCapsule(Capsule capsule) {
        return afileRepository.findByCapsule(capsule);
    }
    
    @Override
    public List<Afile> getAfilesByCapsuleOrderByDateDesc(Capsule capsule) {
        return afileRepository.findByCapsuleOrderByAfregdateDesc(capsule);
    }
    
    @Override
    public List<Afile> getAfilesByDeleteYn(String afdelyn) {
        return afileRepository.findByAfdelyn(afdelyn);
    }
    
    @Override
    public List<Afile> getAfilesByCapsuleAndDeleteYn(Capsule capsule, String afdelyn) {
        return afileRepository.findByCapsuleAndAfdelyn(capsule, afdelyn);
    }
    
    @Override
    public long countAfilesByCapsule(Capsule capsule) {
        return afileRepository.countByCapsule(capsule);
    }
}
