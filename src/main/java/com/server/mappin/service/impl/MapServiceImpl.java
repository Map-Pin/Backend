package com.server.mappin.service.impl;

import com.server.mappin.common.status.ErrorStatus;
import com.server.mappin.exception.handler.MapHandler;
import com.server.mappin.service.MapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class MapServiceImpl implements MapService {
    @Value("${spring.kakao.api.key}")
    private String apiKey;

    @Override
    public Point GetLocalInfo(String address){
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        HttpEntity<Object> httpEntity = getHttpEntity();
        URI builder = UriComponentsBuilder
                .fromUriString(apiUrl)
                .queryParam("query",address)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();
        List<Map<String,Object>> documents = getDocuments(builder, httpEntity);

        if(!documents.isEmpty()){
            Map<String,Object> firstDocument = documents.get(0);
            Object xObject = firstDocument.get("x");
            Object yObject = firstDocument.get("y");
            if(xObject instanceof Double && yObject instanceof Double){
                Double xCoordinate = (Double) xObject;
                Double yCoordinate = (Double) yObject;
                return new Point(xCoordinate,yCoordinate);
            }else if(xObject instanceof String && yObject instanceof String){
                try{
                    Double xCoordinate = Double.parseDouble((String) xObject);
                    Double yCoordinate = Double.parseDouble((String) yObject);
                    return new Point(xCoordinate,yCoordinate);
                }catch (NumberFormatException e){
                    log.info("MapService GetLocalInfo 잘못된 숫자형식입니다");
                }
            }
        } else {
            throw new MapHandler(ErrorStatus.MAP_DONG_NOT_FOUND);
        }
        return null;
    }

    @Override
    public String getDong(Double xCoordinate, Double yCoordinate){
        String apiUrl = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json";
        HttpEntity<Object> httpEntity = getHttpEntity();
        URI builder = UriComponentsBuilder
                .fromUriString(apiUrl)
                .queryParam("x",xCoordinate)
                .queryParam("y",yCoordinate)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();
        List<Map<String,Object>> documents = getDocuments(builder, httpEntity);
        if(!documents.isEmpty()){
            Map<String,Object> firstDocument = documents.get(0);
            Object dong = firstDocument.get("region_3depth_name");
            return (String)dong;
        } else {
            throw new MapHandler(ErrorStatus.MAP_DONG_NOT_FOUND);
        }
    }

    private List<Map<String, Object>> getDocuments(URI builder, HttpEntity<Object> httpEntity) {
        try {
            ResponseEntity<Map<String, Object>> response = new RestTemplate().exchange(builder, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<Map<String, Object>>() {
            });
            Map<String, Object> responseBody = response.getBody();
            return (List<Map<String, Object>>) responseBody.get("documents");
        }
        catch (Exception e){
            throw new MapHandler(ErrorStatus.MAP_NO_DOCUMENT);
        }
    }

    private HttpEntity<Object> getHttpEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","KakaoAK "+apiKey);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        return httpEntity;
    }

}
