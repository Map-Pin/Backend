package com.server.mappin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
@Schema(description = "기본 응답")
public class BaseResponseDto<T> {

  @JsonProperty("isSuccess")
  private String isSuccess;
  private Integer code;
  private T result;

  // 성공한 경우 응답 생성
  public static <T> BaseResponseDto<T> of(String message, Integer code, T data){
    return new BaseResponseDto<>(message, code, data);
  }

  // 실패한 경우 응답 생성
  public static <T> BaseResponseDto<T> onFailure(String message, Integer code, T data){
    return new BaseResponseDto<>(message, code, data);
  }
}
