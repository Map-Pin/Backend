package com.server.mappin.converter;

import com.server.mappin.domain.Lost;
import com.server.mappin.dto.Category.CategoryDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryConverter {

    public static CategoryDTO.CategoryListResponseDTO toCategoryList(List<Lost> losts){
        return CategoryDTO.CategoryListResponseDTO.builder()
                .result(losts.stream().map(lost -> CategoryDTO.CategoryResponseDTO.builder()
                                .id(lost.getId())
                                .title(lost.getTitle())
                                .creatdAt(lost.getCreatedAt())
                                .imageUrl(lost.getImageUrl())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
