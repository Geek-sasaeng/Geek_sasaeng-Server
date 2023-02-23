package shop.geeksasang.service.common;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client s3Client;

    public List<String> uploadFiles(List<MultipartFile> images) throws IOException {
        List<String> result= new ArrayList<>();
        for (MultipartFile image : images) {
            result.add(uploadFile(image.getInputStream(), image.getOriginalFilename(), image.getSize()));
        }
        return result;
    }

    private String uploadFile(InputStream inputStream, String originFileName, Long fileSize) {
        String s3FileName = UUID.randomUUID() + "-" + originFileName;

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(fileSize);
        //그리고 Spring Server에서 S3로 파일을 업로드해야 하는데, 이 때 파일의 사이즈를 ContentLength로 S3에 알려주기 위해서 ObjectMetadata를 사용

        s3Client.putObject(bucket, s3FileName, inputStream, objMeta);
        //그리고 이제 S3 API 메소드인 putObject를 이용하여 파일 Stream을 열어서 S3에 파일을 업로드 합니다.

        return s3Client.getUrl(bucket, s3FileName).toString();
        //그리고 getUrl 메소드를 통해서 S3에 업로드된 사진 URL을 가져오는 방식입니다.
    }
}
