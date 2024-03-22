package com.spordee.user.service;

import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.response.common.CommonResponse;
import reactor.core.publisher.Mono;


public interface UserService {
    Mono<CommonResponse> saveOnboardingUsers(InitialUserSaveRequestDto initialUserSaveRequestDto, CommonResponse commonResponse);
}
