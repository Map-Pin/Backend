package com.server.mappin.exception.handler;

import com.server.mappin.common.BaseErrorCode;
import com.server.mappin.exception.GeneralException;

public class MemberHandler extends GeneralException {
    public MemberHandler(BaseErrorCode errorCode){
        super(errorCode);
    }
}
