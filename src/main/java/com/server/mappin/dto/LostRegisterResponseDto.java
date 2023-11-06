package com.server.mappin.dto;

import com.server.mappin.domain.Lost;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class LostRegisterResponseDto {
    private int statusCode;
    private String isSuccess;
    private Long memberId;
    private Long lostId;
    private String title;
    private String content;
    private LocalDate foundDate;
    private LocalDateTime createdAt;
    private String image;
    private Double x;
    private Double y;
    private String dong;
    private String category;
}
