package com.spordee.user.exceptions;

import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.StatusType;
import com.spordee.user.response.common.CommonResponse;
import com.spordee.user.response.common.MetaData;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends Exception {
    @ExceptionHandler(Exception.class)
    public static CommonResponse handleExceptionRoot(CommonMessages commonMessages,
                                          StatusType statusType,
                                          HttpServletResponse response,
                                          HttpStatus httpStatus,
                                          Exception exception,
                                          CommonResponse commonResponse,
                                          String message) {
        commonResponse.setData(exception.getMessage());
        commonResponse.setMeta(new MetaData(true, commonMessages, httpStatus.value(), message));
        commonResponse.setStatus(statusType);
        switch (httpStatus) {
            case BAD_GATEWAY -> response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            case INTERNAL_SERVER_ERROR -> response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            case NOT_FOUND -> response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            case CONFLICT -> response.setStatus(HttpServletResponse.SC_CONFLICT);
            case LOCKED -> response.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
            case FORBIDDEN -> response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            case NOT_ACCEPTABLE -> response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }


        return commonResponse;
    }
}
