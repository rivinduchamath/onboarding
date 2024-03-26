package com.spordee.user.service;

import com.spordee.user.dto.UpdateUserRequestDto;
import com.spordee.user.dto.request.PersonalInformationRequestDto;
import com.spordee.user.response.common.CommonResponse;
import reactor.core.publisher.Mono;

public interface UpdateUserService {
    Mono<CommonResponse> updatePersonalDetails(PersonalInformationRequestDto updateUserRequestDto, CommonResponse commonResponse);
    Mono<CommonResponse> updateHeightWeightAndSports(UpdateUserRequestDto updateUserRequestDto,String username,CommonResponse commonResponse);
    Mono<CommonResponse> updateSkills(UpdateUserRequestDto updateUserRequestDto,String username,CommonResponse commonResponse);
}
