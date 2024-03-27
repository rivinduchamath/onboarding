package com.spordee.user.service;

import com.spordee.user.dto.response.common.CommonResponse;
import reactor.core.publisher.Mono;

public interface GetUserService {
    Mono<CommonResponse> getInstitutions(CommonResponse commonResponse, String userName);

    Mono<CommonResponse> getPersonalDetails(CommonResponse commonResponse, String username);
}
