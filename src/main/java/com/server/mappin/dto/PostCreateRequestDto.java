package com.server.mappin.dto;

import com.server.mappin.domain.Category;
import com.server.mappin.domain.Location;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PostCreateRequestDto {
    private String title;
    private String content;
    private LocalDate date;
    private String dong;
    private String category;
    private Double x;
    private Double y;

}
