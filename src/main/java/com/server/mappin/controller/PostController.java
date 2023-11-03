package com.server.mappin.controller;


import com.server.mappin.dto.PostCreateRequestDto;
import com.server.mappin.dto.PostCreateResponseDto;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Post API",description = "게시물 관련 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시물 작성",description = "게시물을 작성합니다")
    @ApiResponses({
            @ApiResponse(content = @Content(schema = @Schema(implementation = PostCreateResponseDto.class)))
    })
    @PutMapping("/post")
    public ResponseEntity<?> create(@RequestBody PostCreateRequestDto postCreateDto, Authentication authentication){
        try{
            String email = authentication.getName();
            PostCreateResponseDto postCreateResponseDto = postService.create(postCreateDto, email);
            return new ResponseEntity<>(postCreateResponseDto,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
        }

    }
}