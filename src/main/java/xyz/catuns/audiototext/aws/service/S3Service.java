package xyz.catuns.audiototext.aws.service;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    String uploadFile(MultipartFile file, String key) throws IOException;
    S3Object getFile(String key);
    void deleteFile(String key);
    String getPresignedUrl(String key, long expirationTime);
}
