package com.server.mappin.converter;

import com.server.mappin.domain.Lost;
import com.server.mappin.dto.Location.LocationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LocationConverter {

    public static LocationDTO.LocationListResponseDTO toLocationList(List<Lost>losts){
        return LocationDTO.LocationListResponseDTO.builder()
                .result(losts.stream().map(lost -> LocationDTO.LocationResponseDTO.builder()
                                .id(lost.getId())
                                .title(lost.getTitle())
                                .createdAt(lost.getCreatedAt())
                                .imageUrl(lost.getImageUrl())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
