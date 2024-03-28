package com.spordee.user.service;

import com.spordee.user.dto.response.common.CommonResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetUserService {
    Mono<CommonResponse> getUsersAllData(CommonResponse commonResponse, String username);

//    Mono<CommonResponse> getAllData(CommonResponse commonResponseMono);
}
