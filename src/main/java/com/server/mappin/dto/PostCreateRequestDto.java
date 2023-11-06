package com.server.mappin.dto;

import com.server.mappin.domain.Category;
import com.server.mappin.domain.Location;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class PostCreateRequestDto {
    private String title;
    private String content;
    private String lostDate;
    private MultipartFile image;
    private Double x;
    private Double y;
    private String category;

}
