package com.spordee.user.util;

import com.spordee.user.entity.objects.InstituteDetails;
import com.spordee.user.entity.profiledata.ProfileData;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.StatusType;
import com.spordee.user.dto.response.common.CommonResponse;
import com.spordee.user.dto.response.common.MetaData;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ResponseMethods {
   public static Mono<CommonResponse> userNotFound(CommonResponse commonResponse){
       commonResponse.setStatus(StatusType.STATUS_FAIL);
       commonResponse.setMeta(new MetaData(true, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error occurred while updating personal details"));
       return Mono.just(commonResponse);
    }

    public static Mono<CommonResponse> internalServerError(CommonResponse commonResponse){
        commonResponse.setStatus(StatusType.STATUS_FAIL);
        commonResponse.setMeta(new MetaData(true, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error on Server"));
        return Mono.just(commonResponse);
    }
    public static Mono<CommonResponse> profileDataIsNull(CommonResponse commonResponse){
        commonResponse.setStatus(StatusType.STATUS_FAIL);
        commonResponse.setMeta(new MetaData(true, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error occurred while updating personal details"));
        return Mono.just(commonResponse);
    }
    public static Mono<CommonResponse> profileDataUpdateSuccess(CommonResponse commonResponse){
        commonResponse.setStatus(StatusType.STATUS_SUCCESS);
        commonResponse.setMeta(new MetaData(false, CommonMessages.REQUEST_SUCCESS, 200, "Update Success"));
        return Mono.just(commonResponse);
    }
    public static Mono<CommonResponse> getInstitutions(Mono<List<InstituteDetails>> institutions, CommonResponse commonResponse){
        commonResponse.setStatus(StatusType.STATUS_SUCCESS);
        commonResponse.setData(institutions);
        commonResponse.setMeta(new MetaData(false, CommonMessages.REQUEST_SUCCESS, 200, "Update Success"));
        return Mono.just(commonResponse);
    }
    public static Mono<CommonResponse> primaryUserDataIsNull(CommonResponse commonResponse){
        commonResponse.setStatus(StatusType.STATUS_FAIL);
        commonResponse.setMeta(new MetaData(true, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error occurred while updating personal details"));
        return Mono.just(commonResponse);
    }
}
