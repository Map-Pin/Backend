package com.server.mappin.common.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.mappin.common.BaseErrorCode;
import com.server.mappin.common.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;



@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    //일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    // 멤버 관려 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),

    // 예시,,,
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다."),

    //JWT 토큰 관련 에러
    JWT_BAD_REQUEST(HttpStatus.BAD_REQUEST,"TOKEN400","잘못된 JWT 서명입니다."),
    JWT_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "TOKEN401","유효한 JWT 토큰이 없습니다"),
    JWT_ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,"TOKEN402","액세스 토큰이 만료되었습니다"),
    JWT_TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED,"TOKEN403","지원하지 않는 JWT 토큰입니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDto getReason(){
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .success(false)
                .build();
    }

    @Override
    public ErrorReasonDto getReasonHttpStatus(){
        return ErrorReasonDto.builder()
                .httpStatus(httpStatus)
                .message(message)
                .code(code)
                .success(false)
                .build();
    }

}
