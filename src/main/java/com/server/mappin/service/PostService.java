package com.server.mappin.service;

import com.server.mappin.domain.Category;
import com.server.mappin.domain.Location;
import com.server.mappin.domain.Member;
import com.server.mappin.domain.Post;
import com.server.mappin.dto.PostCreateRequestDto;
import com.server.mappin.dto.PostCreateResponseDto;
import com.server.mappin.dto.PostSearchResponseDto;
import com.server.mappin.repository.CategoryRepository;
import com.server.mappin.repository.LocationRepository;
import com.server.mappin.repository.MemberRepository;
import com.server.mappin.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PostService {
  private final PostRepository postRepository;
  private final LocationRepository locationRepository;
  private final CategoryRepository categoryRepository;
  private final MemberRepository memberRepository;
  private final S3Service s3Service;
  private final MapService mapService;


  @Transactional
  public PostCreateResponseDto create(PostCreateRequestDto postCreateRequestDto, MultipartFile image, String email) throws IOException {
    //String date -> LocalDate로 변경
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(postCreateRequestDto.getLostDate(),formatter);
    //x,y 좌표로 동 검색하기
    String dong = mapService.getDong(postCreateRequestDto.getX(), postCreateRequestDto.getY());
    Optional<Location> locationByDong = locationRepository.findLocationByDong(dong);
    Optional<Category> categoryByName = categoryRepository.findCategoryByName(postCreateRequestDto.getCategory());
    Optional<Member> memberByEmail = memberRepository.findByEmail(email);
    String imageUrl = s3Service.upload(image, "images");
    Location location;
    Category category;
    Member member;
    if (locationByDong.isPresent() && categoryByName.isPresent() && memberByEmail.isPresent()) {
      location = locationByDong.get();
      category = categoryByName.get();
      member = memberByEmail.get();
      Post post = Post.builder()
              .member(member)
              .category(category)
              .location(location)
              .title(postCreateRequestDto.getTitle())
              .x(postCreateRequestDto.getX())
              .y(postCreateRequestDto.getY())
              .lostDate(localDate)
              .createdAt(LocalDateTime.now())
              .content(postCreateRequestDto.getContent())
              .imageUrl(imageUrl)
              .build();

      Post save = postRepository.save(post);
      log.info(save.getTitle());
      return PostCreateResponseDto.builder()
              .statusCode(200)
              .isSuccess("true")
              .postId(save.getId())
              .memberId(member.getId())
              .image(imageUrl)
              .createdAt(post.getCreatedAt())
              .content(post.getContent())
              .x(post.getX())
              .y(post.getY())
              .dong(post.getLocation().getDong())
              .category(post.getCategory().getName())
              .lostDate(post.getLostDate())
              .build();

    }
    return PostCreateResponseDto.builder()
            .statusCode(400)
            .isSuccess("false")
            .build();
  }


  public PostSearchResponseDto search(Long id){
    Optional<Post> postRepositoryById = postRepository.findById(id);
    return postRepositoryById
            .map(post -> PostSearchResponseDto.builder()
                    .statusCode(200)
                    .isSuccess("true")
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
            .orElse(PostSearchResponseDto.builder()
                    .statusCode(400)
                    .isSuccess("false")
                    .build());

  }

}
