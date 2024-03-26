package com.spordee.user.service;

import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.dto.UpdateUserRequestDto;
import com.spordee.user.response.common.CommonResponse;
import reactor.core.publisher.Mono;


public interface UserService {
    Mono<CommonResponse> saveOnboardingUsers(InitialUserSaveRequestDto initialUserSaveRequestDto, CommonResponse commonResponse,String username);

    Mono<CommonResponse> updatePersonalDetails(UpdateUserRequestDto updateUserRequestDto,String username,CommonResponse commonResponse);
    Mono<CommonResponse> updateHeightWeightAndSports(UpdateUserRequestDto updateUserRequestDto,String username,CommonResponse commonResponse);
    Mono<CommonResponse> updateSkills(UpdateUserRequestDto updateUserRequestDto,String username,CommonResponse commonResponse);


}
