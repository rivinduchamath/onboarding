package com.spordee.user.util;

import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.StatusType;
import com.spordee.user.response.common.CommonResponse;
import com.spordee.user.response.common.MetaData;
import reactor.core.publisher.Mono;

public class ResponseMethods {
   public static Mono<CommonResponse> userNotFound(CommonResponse commonResponse){

       commonResponse.setStatus(StatusType.STATUS_FAIL);
       commonResponse.setData("");
       commonResponse.setMeta(new MetaData(true, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error occurred while updating personal details"));
       return Mono.just(commonResponse);
    }

    public static Mono<CommonResponse> internalServerError(CommonResponse commonResponse){

        commonResponse.setStatus(StatusType.STATUS_FAIL);
        commonResponse.setData("");
        commonResponse.setMeta(new MetaData(true, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error on Server"));
        return Mono.just(commonResponse);
    }
    public static Mono<CommonResponse> profileDataIsNull(CommonResponse commonResponse){

        commonResponse.setStatus(StatusType.STATUS_FAIL);
        commonResponse.setData("");
        commonResponse.setMeta(new MetaData(true, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error occurred while updating personal details"));
        return Mono.just(commonResponse);
    }
    public static Mono<CommonResponse> profileDataUpdateSuccess(CommonResponse commonResponse){
        commonResponse.setStatus(StatusType.STATUS_SUCCESS);
        commonResponse.setMeta(new MetaData(false, CommonMessages.REQUEST_SUCCESS, 200, "Update Success"));
        return Mono.just(commonResponse);
    }
    public static Mono<CommonResponse> primaryUserDataIsNull(CommonResponse commonResponse){
        commonResponse.setStatus(StatusType.STATUS_FAIL);
        commonResponse.setData("");
        commonResponse.setMeta(new MetaData(true, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error occurred while updating personal details"));
        return Mono.just(commonResponse);
    }
}
