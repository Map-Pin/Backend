package com.server.mappin.service.impl;

import com.server.mappin.converter.PostConverter;
import com.server.mappin.domain.Category;
import com.server.mappin.domain.Location;
import com.server.mappin.domain.Member;
import com.server.mappin.domain.Post;
import com.server.mappin.dto.Post.PostDTO;
import com.server.mappin.repository.CategoryRepository;
import com.server.mappin.repository.LocationRepository;
import com.server.mappin.repository.MemberRepository;
import com.server.mappin.repository.PostRepository;
import com.server.mappin.service.MapService;
import com.server.mappin.service.PostService;
import com.server.mappin.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;
    private final MapService mapService;

    @Transactional
    @Override
    public PostDTO.PostCreateRP create(PostDTO.PostCreateRQ postCreateRQ, MultipartFile image, String email) throws IOException {
        //String date -> LocalDate로 변경
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(postCreateRQ.getLostDate(),formatter);
        //x,y 좌표로 동 검색하기
        String dong = mapService.getDong(postCreateRQ.getX(), postCreateRQ.getY());
        Optional<Location> locationByDong = locationRepository.findLocationByDong(dong);
        Optional<Category> categoryByName = categoryRepository.findCategoryByName(postCreateRQ.getCategory());
        Optional<Member> memberByEmail = memberRepository.findByEmail(email);
        String imageUrl = s3Service.upload(image, "images");
        Location location;
        Category category;
        Member member;
        if (locationByDong.isPresent() && categoryByName.isPresent() && memberByEmail.isPresent()) {
            location = locationByDong.get();
            category = categoryByName.get();
            member = memberByEmail.get();
            Post post = PostConverter.toPost(postCreateRQ,member,location,category,localDate,imageUrl);

            Post save = postRepository.save(post);
            log.info(save.getTitle());
            return PostConverter.toPostCreate(save);
        }
        return PostDTO.PostCreateRP.builder().build();
    }

    @Transactional
    @Override
    public PostDTO.PostUpdateRP update(Long postId, PostDTO.PostUpdateRQ postUpdateRQ, MultipartFile image, String email) throws IOException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다"));

        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isEmpty()){
            return PostDTO.PostUpdateRP.builder()
                    .build();
        }
        else if(!member.get().getId().equals(post.getMember().getId())){
            return PostDTO.PostUpdateRP.builder().build();
        }

        // 업데이트 필드를 확인하고 필요한 경우 업데이트
        if (postUpdateRQ.getLostDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(postUpdateRQ.getLostDate(), formatter);
            post.setLostDate(localDate);
        }

        if (postUpdateRQ.getX() != null && postUpdateRQ.getY() != null) {
            String dong = mapService.getDong(postUpdateRQ.getX(), postUpdateRQ.getY());
            Optional<Location> locationByDong = locationRepository.findLocationByDong(dong);
            locationByDong.ifPresent(post::setLocation);
        }

        if (postUpdateRQ.getCategory() != null) {
            Optional<Category> categoryByName = categoryRepository.findCategoryByName(postUpdateRQ.getCategory());
            categoryByName.ifPresent(post::setCategory);
        }

        if (postUpdateRQ.getContent() != null) {
            post.setContent(postUpdateRQ.getContent());
        }

        if (postUpdateRQ.getTitle() != null) {
            post.setTitle(postUpdateRQ.getTitle());
        }

        if (image != null) {
            // 이미지 업로드 및 기존 이미지 삭제 처리
            if (!s3Service.findByUrl(post.getImageUrl()).isEmpty()) {
                s3Service.delete(post.getImageUrl());
            }
            String imageUrl = s3Service.upload(image, "images");
            post.setImageUrl(imageUrl);
        }

        // 게시물 업데이트
        Post updatedPost = postRepository.save(post);

        if (updatedPost != null) {
            return PostConverter.toPostUpdate(updatedPost);
        } else {
            return PostDTO.PostUpdateRP.builder()
                    .build();
        }
    }


    @Override
    public PostDTO.PostSearchRP search(Long id){
        Optional<Post> postRepositoryById = postRepository.findById(id);
        return postRepositoryById
                .map(post -> PostDTO.PostSearchRP.builder()
                        .title(post.getTitle())
                        .image(post.getImageUrl())
                        .lostDate(post.getLostDate())
                        .createdAt(post.getCreatedAt())
                        .x(post.getX())
                        .y(post.getY())
                        .category(post.getCategory().getName())
                        .dong(post.getLocation().getDong())
                        .content(post.getContent())
                        .build())
                .orElse(PostDTO.PostSearchRP.builder()
                        .build());

    }
}
