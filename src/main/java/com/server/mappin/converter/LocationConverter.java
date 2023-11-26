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

    public static LocationDTO.LocationListRP toLocationList(List<Lost>losts){
        return LocationDTO.LocationListRP.builder()
                .result(losts.stream().map(lost -> LocationDTO.LocationRP.builder()
                                .id(lost.getId())
                                .title(lost.getTitle())
                                .createdAt(lost.getCreatedAt())
                                .imageUrl(lost.getImageUrl())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
