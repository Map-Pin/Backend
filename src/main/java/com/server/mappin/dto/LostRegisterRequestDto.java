package com.server.mappin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.server.mappin.domain.Category;
import com.server.mappin.domain.Location;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.geo.Point;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class LostRegisterRequestDto {
    private String title;
    private String content;
    private String foundDate;
    private Double x;
    private Double y;
    private String dong;
    private String category;
}
