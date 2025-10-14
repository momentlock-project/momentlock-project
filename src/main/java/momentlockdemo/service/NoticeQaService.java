package momentlockdemo.service;

import java.util.List;
import java.util.Optional;

import momentlockdemo.entity.master.NoticeQa;

public interface NoticeQaService {
    
	public abstract List<NoticeQa> getAllNoticeQa();
	
	public abstract Optional<NoticeQa> getNoticeQaById(Long id);
	
	public abstract NoticeQa insertNoticeQa(NoticeQa noticeQa);
	
	public abstract void deleteNoticeQa(Long id);
}
