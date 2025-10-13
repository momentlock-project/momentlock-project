package momentlockdemo.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

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

    // 버킷 이름은 application.properties에서 가져와도 되고, 상수로 써도 됨
    private final String bucketName = "your-bucket-name"; // properties와 동일하게 맞춰야 함


    @Override
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
    public Afile updateAfile(Afile afile) {
        return afileRepository.save(afile);
    }

    @Override
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

    // ----------------------------- S3 업로드 ----------------------------- //

    @Override
    public String uploadToS3(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);

        return amazonS3.getUrl(bucketName, fileName).toString(); // 업로드된 S3 URL 반환
    }

    // ----------------------------- Capsule 저장 ----------------------------- //

    @Override
    public Afile saveFileToCapsule(MultipartFile file, Capsule capsule) throws IOException {
        // S3 업로드
        String s3Url = uploadToS3(file);

        // DB 저장용 엔티티 생성
        Afile afile = Afile.builder()
                .afsname(file.getOriginalFilename())
                .afcname(s3Url)
                .afcontenttype(file.getContentType())
                .afregdate(LocalDateTime.now())
                .afdelyn("ADDN")
                .capsule(capsule)
                .build();

        // DB 저장
        return afileRepository.save(afile);
    }
}
