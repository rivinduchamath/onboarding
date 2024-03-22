package com.spordee.user.controller;

import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.StatusType;
import com.spordee.user.response.common.CommonResponse;
import com.spordee.user.response.common.MetaData;
import com.spordee.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

import static com.spordee.user.util.StatusCodes.CODE_FORBIDDEN;


@Slf4j
@RestController
@RequestMapping("${api.class.header}")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


//    @PostMapping("${api.class.method}")
//    public CommonResponse saveOnboardingUsers(@RequestBody InitialUserSaveRequestDto initialUserSaveRequestDto,
//                                              Principal principal, HttpServletResponse httpServletResponse) {
//        log.info("LOG::UserController saveOnboardingUsers");
//        CommonResponse commonResponse = new CommonResponse();
//        try {
//            if (principal.getName().isEmpty()) {
//                log.error("LOG::UserController saveOnboardingUsers User Cannot Found Inside the Token " +
//                        "(first Name = {})", initialUserSaveRequestDto.getFirstName());
//                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                commonResponse.setData("Authentication error. Cannot Find User Name");
//                commonResponse.setStatus(StatusType.STATUS_FAIL);
//                commonResponse.setMeta(new MetaData(true, CommonMessages.REQUEST_FAIL, CODE_FORBIDDEN.getCode(),
//                        "Forbidden Access"));
//            } else {
//                String userName = principal.getName();
//                initialUserSaveRequestDto.setUserName(userName);
//                commonResponse = userService.saveOnboardingUsers(initialUserSaveRequestDto, commonResponse,
//                        httpServletResponse);
//                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//                log.debug("LOG::UserController saveOnboardingUsers user name {} Save Success", userName);
//            }
//        } catch (Exception e) {
//            log.error("LOG::UserController saveOnboardingUsers user Save Exception "+
//                    "(first Name = {})", initialUserSaveRequestDto.getFirstName());
//            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            commonResponse.setData(e.getMessage());
//            commonResponse.setStatus(StatusType.STATUS_FAIL);
//            commonResponse.setMeta(new MetaData(true, CommonMessages.FORBIDDEN_ACCESS, CODE_FORBIDDEN.getCode(),
//                    "Security Error"));
//        }
//        return commonResponse;
//    }

    @PostMapping("${api.class.method}")
    public Mono<CommonResponse> saveOnboardingUsers(@RequestBody InitialUserSaveRequestDto initialUserSaveRequestDto,
                                                    Principal principal) {
        log.info("LOG::UserController saveOnboardingUsers");
        return Mono.justOrEmpty(principal)
                .flatMap(user -> {
                    String userName = user.getName();
                    initialUserSaveRequestDto.setUserName(userName);
                    Mono<CommonResponse> commonResponseMono = userService.saveOnboardingUsers(initialUserSaveRequestDto, new CommonResponse());
                    log.debug("LOG::UserController saveOnboardingUsers user name {} Save Success", userName);
                    return commonResponseMono;
                })
                .onErrorResume(exception -> {
                    log.error("LOG::UserController saveOnboardingUsers user Save Exception "+
                    "(first Name = {})", initialUserSaveRequestDto.getFirstName());
                    CommonResponse commonResponse = new CommonResponse();
                    commonResponse.setData(exception.getMessage());
                    commonResponse.setStatus(StatusType.STATUS_FAIL);
                    commonResponse.setMeta(new MetaData(true, CommonMessages.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "System Error"));
                    return Mono.just(commonResponse);
                });
    }
}
