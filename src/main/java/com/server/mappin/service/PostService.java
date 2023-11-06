package com.server.mappin.service;

import com.server.mappin.domain.Category;
import com.server.mappin.domain.Location;
import com.server.mappin.domain.Member;
import com.server.mappin.domain.Post;
import com.server.mappin.dto.PostCreateRequestDto;
import com.server.mappin.dto.PostCreateResponseDto;
import com.server.mappin.repository.CategoryRepository;
import com.server.mappin.repository.LocationRepository;
import com.server.mappin.repository.MemberRepository;
import com.server.mappin.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

  @Transactional
  public PostCreateResponseDto create(PostCreateRequestDto postCreateRequestDto, String email) {
    Optional<Location> locationByDong = locationRepository.findLocationByDong(postCreateRequestDto.getDong());
    Optional<Category> categoryByName = categoryRepository.findCategoryByName(postCreateRequestDto.getCategory());
    Optional<Member> memberByEmail = memberRepository.findByEmail(email);
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
              .lostDate(postCreateRequestDto.getDate())
              .createdAt(LocalDate.now())
              .content(postCreateRequestDto.getContent())
              .build();

      Post save = postRepository.save(post);
      log.info(save.getTitle());
      return PostCreateResponseDto.builder()
              .statusCode(200)
              .isSuccess("true")
              .postId(save.getId())
              .build();

    }
    return PostCreateResponseDto.builder()
            .statusCode(400)
            .isSuccess("false")
            .postId(null)
            .build();
  }
}
