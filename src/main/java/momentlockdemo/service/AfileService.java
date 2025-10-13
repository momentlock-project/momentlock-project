package momentlockdemo.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import momentlockdemo.entity.Afile;
import momentlockdemo.entity.Capsule;

public interface AfileService {

	// 기본 CRUD
	public abstract Afile createAfile(Afile afile);

	public abstract Optional<Afile> getAfileById(Long afid);

	public abstract List<Afile> getAllAfiles();

	public abstract Afile updateAfile(Afile afile);

	public abstract void deleteAfile(Long afid);

	// 추가 비즈니스 로직
	public abstract List<Afile> getAfilesByCapsule(Capsule capsule);

	public abstract List<Afile> getAfilesByCapsuleOrderByDateDesc(Capsule capsule);

	public abstract List<Afile> getAfilesByDeleteYn(String afdelyn);

	public abstract List<Afile> getAfilesByCapsuleAndDeleteYn(Capsule capsule, String afdelyn);

	public abstract long countAfilesByCapsule(Capsule capsule);

	public String uploadToS3(MultipartFile file) throws IOException;
	
    public Afile saveFileToCapsule(MultipartFile file, Capsule capsule) throws IOException;
	
}
