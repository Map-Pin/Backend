package com.server.mappin.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.server.mappin.common.status.ErrorStatus;
import com.server.mappin.exception.handler.S3Handler;
import com.server.mappin.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷

    // S3 파일 업로드
    @Override
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        // MultipartFile -> File
        File convertFile = convert(multipartFile).orElseThrow(() -> new S3Handler(ErrorStatus.S3_NOT_CONVERTABLE)); // 파일을 변환할 수 없으면 에러

        // S3에 저장할 파일명
        String fileName = dirName + "/" + UUID.randomUUID() + "_" + convertFile.getName();

        String uploadImageUrl = "";
        // S3에 파일 업로드
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, convertFile).withCannedAcl(CannedAccessControlList.PublicRead));
            uploadImageUrl = amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (Exception e){
            throw new S3Handler(ErrorStatus.S3_UPLOAD_FAILED);
        }

        // 로컬 파일 삭제
        convertFile.delete();

        return uploadImageUrl;
    }

    @Override
    public String findByUrl(String url){
        try {
            return amazonS3Client.getUrl(bucket, url).toString();
        } catch(Exception e){
            throw new S3Handler(ErrorStatus.S3_URL_NOT_FOUND);
        }
    }

    // S3 파일 삭제
    @Override
    public void delete(String path) {
        try {
            amazonS3Client.deleteObject(bucket, path);
        } catch(Exception e){
            throw new S3Handler(ErrorStatus.S3_DELETE_FAILED);
        }
    }

    // 파일 convert 후 로컬에 업로드
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        } else{
            throw new S3Handler(ErrorStatus.S3_WRONG_PATH); // Or handle the error appropriately here
        }
    }
}
