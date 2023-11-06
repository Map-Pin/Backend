package com.server.mappin.controller;

import com.server.mappin.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping(value = "aws-s3")
@RestController
public class S3Controller {

  private final S3Service s3UploadUtil;

  @PostMapping(name = "S3 파일 업로드", value = "/file")
  public String fileUpload(@RequestParam("files") MultipartFile multipartFile) throws IOException {
    s3UploadUtil.upload(multipartFile, "test"); // test 폴더에 파일 생성
    return "success";
  }

  @DeleteMapping(name = "S3 파일 삭제", value = "/file")
  public String fileDelete(@RequestParam("path") String path) {
    s3UploadUtil.delete(path);
    return "success";
  }

}
