package com.server.mappin.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.server.mappin.common.status.SuccessStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
@Schema(description = "기본 응답")
public class BaseResponseDto<T> {
  @JsonProperty("isSuccess")
  private final boolean isSuccess;
  private final String code;
  private final String message;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T result;

  // 성공한 경우 응답 생성
  public static <T> BaseResponseDto<T> onSuccess(T data){
    return new BaseResponseDto<>(true, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), data);
  }
  public static <T> BaseResponseDto<T> of(String message, String code, T data){
    return new BaseResponseDto<>(true, code, message, data);
  }

  // 실패한 경우 응답 생성
  public static <T> BaseResponseDto<T> onFailure(String message, String code, T data){
    return new BaseResponseDto<>(false,code,message, data);
  }
}
