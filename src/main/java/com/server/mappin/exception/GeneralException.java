package com.server.mappin.exception;

import com.server.mappin.common.BaseErrorCode;
import com.server.mappin.common.BaseResponseDto;
import com.server.mappin.common.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {
    private final BaseErrorCode baseErrorCode;

    public ErrorReasonDto getReason(){
        return this.baseErrorCode.getReason();
    }

    public ErrorReasonDto getReasonHttpStatus(){
        return this.baseErrorCode.getReasonHttpStatus();
    }

}
