package com.server.mappin.converter;

import com.server.mappin.domain.Lost;
import com.server.mappin.dto.Category.CategoryDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryConverter {

    public static CategoryDTO.CategoryListRP toCategoryList(List<Lost> losts){
        return CategoryDTO.CategoryListRP.builder()
                .result(losts.stream().map(lost -> CategoryDTO.CategoryRP.builder()
                                .id(lost.getId())
                                .title(lost.getTitle())
                                .createdAt(lost.getCreatedAt())
                                .imageUrl(lost.getImageUrl())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
