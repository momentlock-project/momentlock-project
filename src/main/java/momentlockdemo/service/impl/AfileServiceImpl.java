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
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

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
    private final AmazonS3 amazonS3;
    
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    
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
    
    public void uploadFileToS3(MultipartFile file, Capsule capsule) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata));

        String s3Url = amazonS3.getUrl(bucket, fileName).toString();

        Afile afile = new Afile();
        afile.setAfcname(file.getOriginalFilename());
        afile.setAfsname(fileName);
        afile.setAfcontenttype(file.getContentType());
        afile.setCapsule(capsule);
        afile.setAfdelyn("N");
        afileRepository.save(afile);
    }
}
