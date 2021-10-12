package config;

import Utils.FileUtils;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
public class AmazonClient {

    private AmazonS3 amazonS3;
    @Value("${amazonProperties_endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties_bucketName}")
    private String bucketName;
    @Value("${amazonProperties_accessKey}")
    private String accessKey;
    @Value("${amazonProperties_secretKey}")
    private String secretKey;


    @PostConstruct
    private void initializeAmazon(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        amazonS3 = new AmazonS3Client(awsCredentials);
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadFile(MultipartFile multipartFile) {

        String fileUrl = "";
        try {
            File file = FileUtils.convertMultiPartToFile(multipartFile);
            String fileName = FileUtils.generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

}
