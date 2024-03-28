package com.spordee.user.controller;

import com.spordee.user.annotations.CurrentUser;
import com.spordee.user.dto.response.common.CommonResponse;
import com.spordee.user.service.GetUserService;
import com.spordee.user.service.UpdateUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

import static com.spordee.user.util.ResponseMethods.internalServerError;
import static com.spordee.user.util.ResponseMethods.userNotFound;

@Slf4j
@RestController
@RequestMapping("${api.class.class-load-user.header}")
@RequiredArgsConstructor
public class GetController {
    private final GetUserService getUserService;
    @GetMapping("${api.class.class-load-user.methods.all}")
    public Mono<CommonResponse> getAllData(@CurrentUser Principal principal){
        String username = principal.getName();
        CommonResponse commonResponse = new CommonResponse();
        if(username.isBlank()){
            return userNotFound(commonResponse);
        }
        log.info("getInstitutions Personal details getInstitutions for user Id {}",principal.getName());

        return getUserService.getAllData(commonResponse, username)
                .onErrorResume(exception -> {
                    log.error("Error occurred while getInstitutions personal details for user {}", username);
                    return internalServerError(commonResponse);
                });
    }
}
