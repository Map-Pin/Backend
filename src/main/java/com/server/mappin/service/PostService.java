package com.server.mappin.service;

import com.server.mappin.dto.Post.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PostService {
  PostDTO.PostCreateRP create(PostDTO.PostCreateRQ postCreateRQ, MultipartFile image, String email) throws IOException;

  PostDTO.PostUpdateRP update(Long postId, PostDTO.PostUpdateRQ postUpdateRQ, MultipartFile image, String email) throws IOException;

  PostDTO.PostSearchRP search(Long id);
}
