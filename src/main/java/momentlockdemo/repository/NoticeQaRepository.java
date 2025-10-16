package momentlockdemo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import momentlockdemo.entity.master.NoticeQa;

public interface NoticeQaRepository extends JpaRepository<NoticeQa, Long> {
	

	    @Override
	    Optional<NoticeQa> findById(Long id);

	    @Override
	    <S extends NoticeQa> S save(S entity);
	    
	    @Override
	    void deleteById(Long id);
	    
	    Page<NoticeQa> findByType(String type, Pageable pageable);
	
}
