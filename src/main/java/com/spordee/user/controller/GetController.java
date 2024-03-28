package com.spordee.user.controller;

import com.spordee.user.annotations.CurrentUser;
import com.spordee.user.dto.response.common.CommonResponse;
import com.spordee.user.service.GetUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

import static com.spordee.user.util.ResponseMethods.*;

@Slf4j
@RestController
@RequestMapping("${api.class.class-load-user.header}")
@RequiredArgsConstructor
public class GetController {
    private final GetUserService getUserService;
    @GetMapping("${api.class.class-load-user.methods.get-user}")
    public Mono<CommonResponse> getUsersAllData(@CurrentUser Principal principal){
        String username = principal.getName();
        CommonResponse commonResponse = new CommonResponse();
        if(username.isBlank()){
            return userNotFound(commonResponse);
        }
        log.info("GetController getUsersAllData for user Id {}",principal.getName());

        return getUserService.getUsersAllData(commonResponse, username)
                .onErrorResume(exception -> {
                    log.error("LOG:: Error GetController getUsersAllData details for user {}", username);
                    return internalServerError(commonResponse);
                });
    }
//    @GetMapping("${api.class.class-load-user.methods.get-all}")
//    @PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
//    public Mono<CommonResponse> getAllData(@CurrentUser Principal principal){
//        String username = principal.getName();
//        CommonResponse commonResponse = new CommonResponse();
//        if(username.isBlank()){
//            return usersNotFound(commonResponse);
//        }
//        log.info("GetController getAllData for user Id {}",principal.getName());
//
//        return getUserService.getAllData(commonResponse)
//                .onErrorResume(exception -> {
//                    log.error("LOG:: Error GetController getUsersAllData details for user {}", username);
//                    return internalServerError(commonResponse);
//                });
//        }
}
