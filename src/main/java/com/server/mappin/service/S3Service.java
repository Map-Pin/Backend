package com.server.mappin.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


public interface S3Service {

  // S3 파일 업로드
  String upload(MultipartFile multipartFile, String dirName) throws IOException;

  String findByUrl(String url);

  // S3 파일 삭제
  void delete(String path);

}
