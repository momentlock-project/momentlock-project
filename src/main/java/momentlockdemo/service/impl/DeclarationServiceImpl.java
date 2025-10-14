package momentlockdemo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Declaration;
import momentlockdemo.entity.Member;
import momentlockdemo.repository.DeclarationRepository;
import momentlockdemo.service.DeclarationService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeclarationServiceImpl implements DeclarationService {
    
    private final DeclarationRepository declarationRepository;
    
    @Override
    @Transactional
    public Declaration createDeclaration(Declaration declaration) {
        return declarationRepository.save(declaration);
    }
    
    @Override
    public Optional<Declaration> getDeclarationById(Long decid) {
        return declarationRepository.findById(decid);
    }
    
    @Override
    public List<Declaration> getAllDeclarations() {
    	return declarationRepository.findAll();
    }
    
    @Override
    public Page<Declaration> getAllDeclarations(Pageable pageable) {
        return declarationRepository.findAll(pageable);
    }
    
    @Override
    @Transactional
    public Declaration updateDeclaration(Declaration declaration) {
        return declarationRepository.save(declaration);
    }
    
    @Override
    @Transactional
    public void deleteDeclaration(Long decid) {
        declarationRepository.deleteById(decid);
    }
    
    @Override
    public List<Declaration> getDeclarationsByMember(Member member) {
        return declarationRepository.findByMember(member);
    }
    
    @Override
    public List<Declaration> getDeclarationsByCapsule(Capsule capsule) {
        return declarationRepository.findByCapsule(capsule);
    }
    
    @Override
    public List<Declaration> getDeclarationsByCategory(String deccategory) {
        return declarationRepository.findByDeccategory(deccategory);
    }
    
    @Override
    public List<Declaration> getDeclarationsByComplete(String deccomplete) {
        return declarationRepository.findByDeccomplete(deccomplete);
    }
    
    @Override
    public List<Declaration> getDeclarationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return declarationRepository.findByDecregdateBetween(startDate, endDate);
    }
    
    @Override
    public long countDeclarationsByComplete(String deccomplete) {
        return declarationRepository.countByDeccomplete(deccomplete);
    }
}
