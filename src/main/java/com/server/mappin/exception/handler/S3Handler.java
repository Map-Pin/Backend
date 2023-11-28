package com.server.mappin.exception.handler;

import com.server.mappin.common.BaseErrorCode;
import com.server.mappin.exception.GeneralException;

public class S3Handler extends GeneralException {
  public S3Handler(BaseErrorCode baseErrorCode) {
    super(baseErrorCode);
  }
}
