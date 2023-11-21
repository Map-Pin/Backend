package com.server.mappin.converter;

import com.server.mappin.domain.Category;
import com.server.mappin.domain.Location;
import com.server.mappin.domain.Member;
import com.server.mappin.domain.Post;
import com.server.mappin.dto.Post.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostConverter {

    public static Post toPost(PostDTO.PostCreateRequestDto postCreateRequestDto,
                              Member member,
                              Location location,
                              Category category,
                              LocalDate localDate,
                              String imageUrl){
        return Post.builder()
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
    }

    public static PostDTO.PostCreateResponseDto toPostCreate(Post post){
        return PostDTO.PostCreateResponseDto.builder()
                .title(post.getTitle())
                .postId(post.getId())
                .memberId(post.getMember().getId())
                .image(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .content(post.getContent())
                .x(post.getX())
                .y(post.getY())
                .dong(post.getLocation().getDong())
                .category(post.getCategory().getName())
                .lostDate(post.getLostDate())
                .build();
    }

    public static PostDTO.PostUpdateResponseDto toPostUpdate(Post post){
        return PostDTO.PostUpdateResponseDto.builder()
                .postId(post.getId())
                .memberId(post.getMember().getId())
                .title(post.getTitle())
                .image(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .content(post.getContent())
                .x(post.getX())
                .y(post.getY())
                .dong(post.getLocation().getDong())
                .category(post.getCategory().getName())
                .lostDate(post.getLostDate())
                .build();
    }
}
