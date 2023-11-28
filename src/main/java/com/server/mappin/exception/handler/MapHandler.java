package com.server.mappin.exception.handler;

import com.server.mappin.common.BaseErrorCode;
import com.server.mappin.exception.GeneralException;

public class MapHandler extends GeneralException {
  public MapHandler(BaseErrorCode errorCode) {super(errorCode);}
}
