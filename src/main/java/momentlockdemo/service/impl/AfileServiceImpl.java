package momentlockdemo.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Afile;
import momentlockdemo.entity.Capsule;
import momentlockdemo.repository.AfileRepository;
import momentlockdemo.service.AfileService;

@Service
@RequiredArgsConstructor
public class AfileServiceImpl implements AfileService {

    private final AfileRepository afileRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

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
        Optional<Afile> afile = afileRepository.findById(afid);
        if (afile.isPresent()) {
            // S3에서 파일 삭제
            deleteFromS3(afile.get().getAfcname());
            // DB에서 삭제
            afileRepository.deleteById(afid);
        }
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

    // S3 업로드
    @Override
    public String uploadToS3(MultipartFile file) throws IOException {
        // 고유한 파일명 생성 (UUID + 원본 파일명)
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID().toString() + extension;

        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        // S3에 업로드
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));

        // 업로드된 파일의 URL 반환
        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    // S3에서 파일 삭제
    public void deleteFromS3(String fileUrl) {
        try {
            // URL에서 파일명 추출
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        } catch (Exception e) {
            throw new RuntimeException("S3 파일 삭제 실패", e);
        }
    }

    // Capsule에 파일 저장
    @Override
    @Transactional
    public Afile saveFileToCapsule(MultipartFile file, Capsule capsule) throws IOException {
        // S3 업로드
        String s3Url = uploadToS3(file);

        // DB 저장용 엔티티 생성
        Afile afile = Afile.builder()
                .afsname(file.getOriginalFilename())
                .afcname(s3Url)
                .afcontenttype(file.getContentType())
                .afdelyn("ADDN")
                .capsule(capsule)
                .build();

        // Capsule의 파일 카운트 증가
        capsule.setCapafilecount((capsule.getCapafilecount() == null ? 0L : capsule.getCapafilecount()) + 1);

        return afileRepository.save(afile);
    }

    // 파일 교체 (기존 파일 삭제 후 새 파일 업로드)
    @Transactional
    public Afile replaceFileToCapsule(MultipartFile newFile, Capsule capsule) throws IOException {
        // 기존 파일 목록 조회
        List<Afile> existingFiles = getAfilesByCapsule(capsule);
        
        // 기존 파일 삭제
        for (Afile oldFile : existingFiles) {
            deleteFromS3(oldFile.getAfcname());
            afileRepository.delete(oldFile);
        }

        // 새 파일 업로드
        return saveFileToCapsule(newFile, capsule);
    }
}