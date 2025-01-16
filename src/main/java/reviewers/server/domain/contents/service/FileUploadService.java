package reviewers.server.domain.contents.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public String upload(MultipartFile image) {
        validateImage(image);
        return uploadImageToS3(image);
    }

    private void validateImage(MultipartFile image) {
        if (image.isEmpty() || image.getOriginalFilename() == null) {
            throw new BaseErrorException(ErrorType._NULL_IMAGE);
        }

        String filename = image.getOriginalFilename();
        String extension = getFileExtension(filename);
        validateExtension(extension);
    }

    private String getFileExtension(String filename) {
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            throw new BaseErrorException(ErrorType._NO_EXTENSION);
        }
        return filename.substring(lastIndexOf + 1).toLowerCase();
    }

    private void validateExtension(String extension) {
        List<String> allowedExtensions = List.of("jpg", "jpeg", "png");
        if (!allowedExtensions.contains(extension)) {
            throw new BaseErrorException(ErrorType._INVALID_FILE_EXTENSION);
        }
    }

    private String uploadImageToS3(MultipartFile image) {
        String originalFilename = Objects.requireNonNull(image.getOriginalFilename());
        String extension = getFileExtension(originalFilename);
        String s3Key = generateS3Key(originalFilename);

        try (InputStream inputStream = image.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(image.getSize());
            metadata.setContentType("image/" + extension);

            amazonS3Client.putObject(new PutObjectRequest(bucket, s3Key, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return amazonS3Client.getUrl(bucket, s3Key).toString();
        } catch (IOException e) {
            throw new BaseErrorException(ErrorType._IO_EXCEPTION_ON_UPLOAD);
        }
    }

    private String generateS3Key(String originalFilename) {
        return UUID.randomUUID().toString().substring(0, 10) + "_" + originalFilename;
    }

    public void deleteImageByUrl(String imageUrl) {
        String key = extractKeyFromImageUrl(imageUrl);
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
        } catch (Exception e) {
            throw new BaseErrorException(ErrorType._IO_EXCEPTION_ON_UPLOAD);
        }
    }

    private String extractKeyFromImageUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            return URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8).substring(1);
        } catch (MalformedURLException e) {
            throw new BaseErrorException(ErrorType._IO_EXCEPTION_ON_UPLOAD);
        }
    }
}

