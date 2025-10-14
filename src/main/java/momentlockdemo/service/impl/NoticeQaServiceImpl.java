package momentlockdemo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional; 

import momentlockdemo.entity.master.NoticeQa;
import momentlockdemo.repository.NoticeQaRepository;
import momentlockdemo.service.NoticeQaService;

@Service 
public class NoticeQaServiceImpl implements NoticeQaService {
	
	@Autowired
	private NoticeQaRepository noticeQaRepository;
	
	@Override
	@Transactional(readOnly = true) 
	public List<NoticeQa> getAllNoticeQa() {
		return noticeQaRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<NoticeQa> getNoticeQaById(Long id) {
		return noticeQaRepository.findById(id);
	}
	
	@Override
	@Transactional
	public NoticeQa insertNoticeQa(NoticeQa noticeQa) {
		return noticeQaRepository.save(noticeQa);
	}
	
	@Override
	@Transactional
	public void deleteNoticeQa(Long id) {
		noticeQaRepository.deleteById(id);
	}

	@Override
	public Page<NoticeQa> getAllNoticeQa(Pageable pageable) {
		return noticeQaRepository.findAll(pageable);
	}
}
