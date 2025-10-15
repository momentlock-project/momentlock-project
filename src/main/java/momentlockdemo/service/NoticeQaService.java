package momentlockdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import momentlockdemo.entity.master.NoticeQa;

public interface NoticeQaService {
    
	public abstract List<NoticeQa> getAllNoticeQa();
	
	public abstract Optional<NoticeQa> getNoticeQaById(Long id);
	
	public abstract NoticeQa insertNoticeQa(NoticeQa noticeQa);
	
	public abstract Page<NoticeQa> getPageNoticeQa(Pageable pageable);
	
	public abstract void deleteNoticeQa(Long id);
}
