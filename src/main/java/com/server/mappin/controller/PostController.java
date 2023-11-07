package com.server.mappin.controller;


import com.server.mappin.dto.PostCreateRequestDto;
import com.server.mappin.dto.PostCreateResponseDto;
import com.server.mappin.dto.PostSearchResponseDto;
import com.server.mappin.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "Post API",description = "게시물 관련 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시물 작성",description = "게시물을 작성합니다")
    @ApiResponses({
            @ApiResponse(responseCode ="200",description ="게시물 작성 성공",content = @Content(schema = @Schema(implementation = PostCreateResponseDto.class))),
            @ApiResponse(responseCode ="400",description ="게시물 작성 실패",content = @Content(schema = @Schema(implementation = PostCreateResponseDto.class)))
    })
    @PutMapping("/post")
    public ResponseEntity<?> create(@ModelAttribute PostCreateRequestDto postCreateDto, Authentication authentication) throws IOException {
        try{
            String email = authentication.getName();
            PostCreateResponseDto postCreateResponseDto = postService.create(postCreateDto, email);
            return new ResponseEntity<>(postCreateResponseDto,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "게시물 조회",description = "게시물 Id로 게시물을 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "게시물 조회 성공",content = @Content(schema = @Schema(implementation = PostSearchResponseDto.class))),
            @ApiResponse(responseCode = "400",description = "게시물 조회 실패",content = @Content(schema = @Schema(implementation = PostCreateResponseDto.class)))
    })
    @GetMapping("/post/{post_id}")
    public ResponseEntity<?> search(@PathVariable("post_id") long id){
        try{
            PostSearchResponseDto search = postService.search(id);
            return new ResponseEntity<>(search,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>("에러가 발생했습니다",HttpStatus.CONFLICT);
        }
    }
}
