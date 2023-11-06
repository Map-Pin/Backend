package com.server.mappin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.geo.Point;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MapService {

    @Value("${spring.kakao.api.key}")
    private String apiKey;

    public Point GetLocalInfo(String address){
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","KakaoAK "+apiKey);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        URI builder = UriComponentsBuilder
                .fromUriString(apiUrl)
                .queryParam("query",address)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();
        ResponseEntity<Map<String,Object>> response = new RestTemplate().exchange(builder, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<Map<String,Object>>() {});
        Map<String,Object> responseBody = response.getBody();
        List<Map<String,Object>> documents = (List<Map<String, Object>>)responseBody.get("documents");
        if(!documents.isEmpty()){
            Map<String,Object> firstDocument = documents.get(0);
            Object xObject = firstDocument.get("x");
            Object yObject = firstDocument.get("y");
            if(xObject instanceof Double && yObject instanceof Double){
                Double xCoordinate = (Double) xObject;
                Double yCoordinate = (Double) yObject;
                Point point = new Point(xCoordinate,yCoordinate);
                return new Point(xCoordinate,yCoordinate);
            }else if(xObject instanceof String && yObject instanceof String){
                try{
                    Double xCoordinate = Double.parseDouble((String) xObject);
                    Double yCoordinate = Double.parseDouble((String) yObject);
                    return new Point(xCoordinate,yCoordinate);
                }catch (NumberFormatException e){
                    log.error(e.getMessage());
                }
            }
        }
        return null;
    }

    public String getDong(Double xCoordinate, Double yCoordinate){
        String apiUrl = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","KakaoAK "+apiKey);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        System.out.println("xCoordinate = " + xCoordinate);
        URI builder = UriComponentsBuilder
                .fromUriString(apiUrl)
                .queryParam("x",xCoordinate)
                .queryParam("y",yCoordinate)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();
        ResponseEntity<Map<String,Object>> response = new RestTemplate().exchange(builder, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<Map<String,Object>>() {});
        Map<String,Object> responseBody = response.getBody();
        List<Map<String,Object>> documents = (List<Map<String, Object>>)responseBody.get("documents");
        if(!documents.isEmpty()){
            Map<String,Object> firstDocument = documents.get(0);
            Object dong = firstDocument.get("region_3depth_name");
            return (String)dong;
        }
        return null;
    }
}
