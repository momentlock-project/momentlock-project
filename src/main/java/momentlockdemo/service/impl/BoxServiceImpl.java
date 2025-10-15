package momentlockdemo.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import momentlockdemo.dto.BoxLikeCountDto;
import momentlockdemo.entity.Box;
import momentlockdemo.repository.BoxRepository;
import momentlockdemo.service.BoxService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoxServiceImpl implements BoxService {
    
    private final BoxRepository boxRepository;
    
    @Override
    @Transactional
    public Box createBox(Box box) {
        return boxRepository.save(box);
    }
    
    @Override
    public Optional<Box> getBoxById(Long boxid) {
        return boxRepository.findById(boxid);
    }
    
    @Override
    public List<Box> getAllBoxes() {
        return boxRepository.findAll();
    }
    
    @Override
    @Transactional
    public Box updateBox(Box box) {
        return boxRepository.save(box);
    }
    
    @Override
    @Transactional
    public void deleteBox(Long boxid) {
        boxRepository.deleteById(boxid);
    }
    
    @Override
    public List<Box> searchBoxByName(String boxname) {
        return boxRepository.findByBoxnameContaining(boxname);
    }
    
    @Override
    public Optional<Box> getBoxByInviteCode(String boxinvitecode) {
        return boxRepository.findByBoxinvitecode(boxinvitecode);
    }
    
    @Override
    public List<Box> searchBoxByLocation(String boxlocation) {
        return boxRepository.findByBoxlocationContaining(boxlocation);
    }
    
    @Override
    public List<Box> getBoxesOpenBefore(LocalDateTime date) {
        return boxRepository.findByBoxopendateBefore(date);
    }
    
    @Override
    public List<Box> getBoxesOpenAfter(LocalDateTime date) {
        return boxRepository.findByBoxopendateAfter(date);
    }
    
    @Override
    public Optional<Box> getBoxByBuryCode(String boxburycode) {
        return boxRepository.findByBoxburycode(boxburycode);
    }

    
    @Override
	public Page<BoxLikeCountDto> getPagedPopularBox(int currPage, int size) {
		Pageable pageable = PageRequest.of(currPage, size);
		return boxRepository.getPopularBoxPage(pageable);

	}
    
    
	@Override
	public Page<Box> getPagedBoxList(int currPage, int size) {
		
		Pageable pageable = PageRequest.of(currPage, size);
//		findAll<Object>: Object 타입 Page 객체를 리턴하는 JpaRepository 기본 메서드
		return boxRepository.findAll(pageable);
		
		
	}
	
	@Override
	public Page<Box> getAllBoxPage(Pageable pageable) {
		return boxRepository.findAll(pageable);
	}

}
