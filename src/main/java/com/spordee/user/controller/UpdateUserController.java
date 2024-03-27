package com.spordee.user.controller;

import com.spordee.user.annotations.CurrentUser;
import com.spordee.user.dto.request.*;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.StatusType;
import com.spordee.user.dto.response.common.CommonResponse;
import com.spordee.user.dto.response.common.MetaData;
import com.spordee.user.service.UpdateUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import java.security.Principal;

import static com.spordee.user.util.ResponseMethods.internalServerError;
import static com.spordee.user.util.ResponseMethods.userNotFound;

@Slf4j
@RestController
@RequestMapping("${api.class.class-update-user.header}")
@RequiredArgsConstructor
public class UpdateUserController {
    private final UpdateUserService userService;

    @PatchMapping("${api.class.class-update-user.methods.personal-details}")
    public Mono<CommonResponse> updatePersonalDetails(@RequestBody PersonalInformationRequestDto updateUserRequestDto, @CurrentUser Principal principal){
         String username = principal.getName();
        CommonResponse commonResponse = new CommonResponse();
        if(username.isBlank()){
         return userNotFound(commonResponse);
        }
        updateUserRequestDto.setUserName(username);
        log.info("Update Personal details for user Id {}",updateUserRequestDto.getName());

        return userService.updatePersonalDetails(updateUserRequestDto, commonResponse)
                .onErrorResume(exception -> {
                    log.error("Error occurred while updating personal details for user {}", username, exception);
                  return internalServerError(commonResponse);
                });

    }

    @PatchMapping("${api.class.class-update-user.methods.specs}")
    public Mono<CommonResponse> updateSpecs(@RequestBody SpecsInfoRequestDto specsInfoRequestDto ,@CurrentUser Principal principal){
       String username = principal.getName();
        CommonResponse commonResponse = new CommonResponse();
        if(username.isBlank()){
            return userNotFound(commonResponse);
        }
        specsInfoRequestDto.setUserName(username);
        log.info("Update Personal details for user Id {}",specsInfoRequestDto.getUserName());

        return userService.updateSpecs(specsInfoRequestDto, commonResponse)
                .onErrorResume(exception -> {
                    log.error("Error occurred while updating personal details for user {}", username, exception);
                    CommonResponse errorResponse = new CommonResponse();
                    errorResponse.setStatus(StatusType.STATUS_FAIL);
                    errorResponse.setData(exception.getMessage());
                    errorResponse.setMeta(new MetaData(false, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error occurred while updating personal details"));
                    return Mono.just(errorResponse);
                });

    }

    @PatchMapping("${api.class.class-update-user.methods.skills}")
    public Mono<CommonResponse> updateSkills(@RequestBody UpdateSkillsRequest updateSkillsRequest ,@CurrentUser Principal principal){
        String username = principal.getName();
        CommonResponse commonResponse = new CommonResponse();
        if(username.isBlank()){
            return userNotFound(commonResponse);
        }
        updateSkillsRequest.setUserName(username);
        log.info("Update Personal details for user Id {}",updateSkillsRequest.getUserName());

        return userService.updateSkillsAndSports(updateSkillsRequest, new CommonResponse())
                .onErrorResume(exception -> {
                    log.error("Error occurred while updating personal details for user {} ", username);
                    CommonResponse errorResponse = new CommonResponse();
                    errorResponse.setStatus(StatusType.STATUS_FAIL);

                    errorResponse.setMeta(new MetaData(false, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error occurred while updating personal details"));
                    return Mono.just(errorResponse);
                });

    }

    @PatchMapping("${api.class.class-update-user.methods.achievements}")
    public Mono<CommonResponse> updateAchievements(AchievementRequest achievementRequest ,@CurrentUser Principal principal){
        String username = principal.getName();
        CommonResponse commonResponse = new CommonResponse();
        if(username.isBlank()){
            return userNotFound(commonResponse);
        }
        achievementRequest.setUserName(username);
        log.info("Update Personal details updateAchievements for user Id {}",achievementRequest.getUserName());

        return userService.updateAchievements(achievementRequest, new CommonResponse())
                .onErrorResume(exception -> {
                    log.error("Error occurred while updating personal details for user {}", username);
                    CommonResponse errorResponse = new CommonResponse();
                    errorResponse.setStatus(StatusType.STATUS_FAIL);
                    errorResponse.setMeta(new MetaData(false, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error occurred while updating personal details"));
                    return Mono.just(errorResponse);
                });

    }

    @PatchMapping("${api.class.class-update-user.methods.institution}")
    public Mono<CommonResponse> updateInstitution(InstitutionsRequestDto institutionsRequestDto,@CurrentUser Principal principal){
         String username = principal.getName();
        CommonResponse commonResponse = new CommonResponse();
        if(username.isBlank()){
            return userNotFound(commonResponse);
        }
        log.info("Update Personal details updateInstitution for user Id {}",principal.getName());
        institutionsRequestDto.setUserName(username);
        return userService.updateInstitution(institutionsRequestDto, commonResponse)
                .onErrorResume(exception -> {
                    log.error("Error occurred while updating personal details for user {}", username);
                    CommonResponse errorResponse = new CommonResponse();
                    errorResponse.setStatus(StatusType.STATUS_FAIL);
                    errorResponse.setMeta(new MetaData(false, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error occurred while updating personal details"));
                    return Mono.just(errorResponse);
                });

    }

}
