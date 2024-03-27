package com.spordee.user.service;

import com.spordee.user.dto.request.*;
import com.spordee.user.dto.response.common.CommonResponse;
import reactor.core.publisher.Mono;

public interface UpdateUserService {
    Mono<CommonResponse> updatePersonalDetails(PersonalInformationRequestDto updateUserRequestDto, CommonResponse commonResponse);

    Mono<CommonResponse> updateSpecs(SpecsInfoRequestDto specsInfoRequestDto, CommonResponse commonResponse);

    Mono<CommonResponse> updateSkillsAndSports(UpdateSkillsRequest updateSkillsRequest, CommonResponse commonResponse);

    Mono<CommonResponse> updateAchievements(AchievementRequest achievementRequest, CommonResponse commonResponse);

    Mono<CommonResponse> updateInstitution(InstitutionsRequestDto institutionsRequestDto, CommonResponse commonResponse);


}
