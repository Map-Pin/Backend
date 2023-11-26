package com.server.mappin.controller;


import com.server.mappin.dto.Post.*;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Post API",description = "게시물 관련 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시물 작성",description = "Content-type은 multipart/form-data이지만 info는 application/json입니다")
    @ApiResponses({
            @ApiResponse(responseCode ="200",description ="게시물 작성 성공",content = @Content(schema = @Schema(implementation = PostDTO.PostCreateRP.class))),
            @ApiResponse(responseCode ="400",description ="게시물 작성 실패",content = @Content(schema = @Schema(implementation = PostDTO.PostCreateRP.class)))
    })
    @PutMapping(value = "/post/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @RequestPart("image")MultipartFile image,
            @RequestPart("info") PostDTO.PostCreateRQ postCreateDto,
            Authentication authentication
            ) throws IOException {
        try{
            String email = authentication.getName();
            PostDTO.PostCreateRP postCreateRP = postService.create(postCreateDto,image, email);
            return new ResponseEntity<>(postCreateRP,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "게시물 수정",description = "Content-type은 multipart/form-data이지만 info는 application/json입니다")
    @ApiResponses({
            @ApiResponse(responseCode ="200",description ="게시물 수정 성공",content = @Content(schema = @Schema(implementation = PostDTO.PostUpdateRP.class))),
            @ApiResponse(responseCode ="400",description ="게시물 수정 실패",content = @Content(schema = @Schema(implementation = PostDTO.PostUpdateRP.class)))
    })
    @PatchMapping(value = "/post/update/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @PathVariable("id") Long id,
            @RequestPart("image") MultipartFile image,
            @RequestPart("info") PostDTO.PostUpdateRQ postUpdateRQ,
            Authentication authentication
    ) throws IOException {
        try{
            String email = authentication.getName();
            PostDTO.PostUpdateRP postUpdateRP = postService.update(id, postUpdateRQ, image, email);
            return new ResponseEntity<>(postUpdateRP,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "게시물 상세 조회",description = "게시물 Id로 게시물을 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "게시물 조회 성공",content = @Content(schema = @Schema(implementation = PostDTO.PostSearchRP.class))),
            @ApiResponse(responseCode = "400",description = "게시물 조회 실패",content = @Content(schema = @Schema(implementation = PostDTO.PostCreateRP.class)))
    })
    @GetMapping("/post/search/id")
    public ResponseEntity<?> search(@RequestParam("id") long id){
        try{
            PostDTO.PostSearchRP search = postService.search(id);
            return new ResponseEntity<>(search,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>("에러가 발생했습니다",HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "게시물 전체 조회",description = "게시물 전체를 조회합니다")
    @ApiResponse(responseCode = "200",description = "게시물 전체 조회 성공",content = @Content(schema=@Schema(implementation = PostSearchAllListResponseDto.class)))
    @GetMapping("/post/search/all")
    public ResponseEntity<?> searchAll(){
        try{
            PostSearchAllListResponseDto postSearchAllListResponseDto = postService.searchAll();
            return new ResponseEntity<>(postSearchAllListResponseDto,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>("에러가 발생했습니다",HttpStatus.CONFLICT);
        }
    }
}
