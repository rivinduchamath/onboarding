package com.spordee.user.controller;

import com.spordee.user.annotations.CurrentUser;
import com.spordee.user.dto.UpdateUserRequestDto;
import com.spordee.user.dto.request.PersonalInformationRequestDto;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.StatusType;
import com.spordee.user.response.common.CommonResponse;
import com.spordee.user.response.common.MetaData;
import com.spordee.user.service.UpdateUserService;
import com.spordee.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import java.security.Principal;

import static com.spordee.user.util.ResponseMethods.internalServerError;
import static com.spordee.user.util.ResponseMethods.userNotFound;

@Slf4j
@RestController
@RequestMapping("${api.class.class-update-user}")
@RequiredArgsConstructor
public class UpdateUserController {
    private final UpdateUserService userService;

    @PatchMapping("${api.class.methods.personal_details}")
    public Mono<CommonResponse> updatePersonalDetails(PersonalInformationRequestDto updateUserRequestDto, Principal principal){
        log.info("Update Personal details for user Id {}",updateUserRequestDto.getName());
        String username = principal.getName();
        CommonResponse commonResponse = new CommonResponse();
        if(username.isBlank()){
         return userNotFound(commonResponse);
        }
        updateUserRequestDto.setUserName(username);
        return userService.updatePersonalDetails(updateUserRequestDto, commonResponse)
                .onErrorResume(exception -> {
                    log.error("Error occurred while updating personal details for user {}", username, exception);
                  return internalServerError(commonResponse);
                });

    }

    @PatchMapping("${api.class.methods.specs}")
    public Mono<CommonResponse> updateSpecs(UpdateUserRequestDto updateUserRequestDto ,Principal principal){
        log.info("Update Personal details for user Id {}",updateUserRequestDto.getName());
        String username = principal.getName();

        return userService.updateHeightWeightAndSports(updateUserRequestDto, username, new CommonResponse())
                .onErrorResume(exception -> {
                    log.error("Error occurred while updating personal details for user {}", username, exception);
                    CommonResponse errorResponse = new CommonResponse();
                    errorResponse.setStatus(StatusType.STATUS_FAIL);
                    errorResponse.setData(exception.getMessage());
                    errorResponse.setMeta(new MetaData(false, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error occurred while updating personal details"));
                    return Mono.just(errorResponse);
                });

    }

    @PatchMapping("${api.class.methods.skills}")
    public Mono<CommonResponse> updateSkills(UpdateUserRequestDto updateUserRequestDto ,Principal principal){
        log.info("Update Personal details for user Id {}",updateUserRequestDto.getName());
        String username = principal.getName();

        return userService.updateHeightWeightAndSports(updateUserRequestDto, username, new CommonResponse())
                .onErrorResume(exception -> {
                    log.error("Error occurred while updating personal details for user {}", username, exception);
                    CommonResponse errorResponse = new CommonResponse();
                    errorResponse.setStatus(StatusType.STATUS_FAIL);
                    errorResponse.setData(exception.getMessage());
                    errorResponse.setMeta(new MetaData(false, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error occurred while updating personal details"));
                    return Mono.just(errorResponse);
                });

    }

    @PatchMapping("${api.class.methods.achievements}")
    public Mono<CommonResponse> updateAchievements(UpdateUserRequestDto updateUserRequestDto ,Principal principal){
        log.info("Update Personal details updateSkills for user Id {}",updateUserRequestDto.getName());
        String username = principal.getName();

        return userService.updateHeightWeightAndSports(updateUserRequestDto, username, new CommonResponse())
                .onErrorResume(exception -> {
                    log.error("Error occurred while updating personal details for user {}", username, exception);
                    CommonResponse errorResponse = new CommonResponse();
                    errorResponse.setStatus(StatusType.STATUS_FAIL);
                    errorResponse.setData(exception.getMessage());
                    errorResponse.setMeta(new MetaData(false, CommonMessages.INTERNAL_SERVER_ERROR, 500, "Error occurred while updating personal details"));
                    return Mono.just(errorResponse);
                });

    }

}
