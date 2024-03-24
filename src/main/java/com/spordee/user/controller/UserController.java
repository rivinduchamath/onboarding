package com.spordee.user.controller;

import com.spordee.user.configurations.Entity.SpordUser;
import com.spordee.user.configurations.Request.SignUpDto;
import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.Device;
import com.spordee.user.enums.StatusType;
import com.spordee.user.response.common.CommonResponse;
import com.spordee.user.response.common.MetaData;
import com.spordee.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
@RestController
@RequestMapping("${api.class.header}")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final WebClient webClient;


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
        AtomicReference<String> deviceId = new AtomicReference<>("");
        AtomicReference<String> device = new AtomicReference<>("");
        // save data into database -> PimraryUserDetails
        Mono<CommonResponse> monoResponse =  userService.saveOnboardingUsers(initialUserSaveRequestDto,new CommonResponse());


        return Mono.justOrEmpty(principal)
                .flatMap(user -> {
                    if (user instanceof UsernamePasswordAuthenticationToken) {
                        SpordUser spordUser = (SpordUser) ((UsernamePasswordAuthenticationToken) user).getPrincipal();
                        deviceId.set(spordUser.getDeviceId());
                        device.set(spordUser.getDevice());
                        SignUpDto signUpRequest = SignUpDto.builder()
                                .username(principal.getName())
                                .device(Device.valueOf(device.get()))
                                .deviceId(deviceId.get())
                                .role(initialUserSaveRequestDto.getRegistrationType())
                                .build();

                        System.out.println("SINGUP DTO : " + signUpRequest);

                        return webClient.post()
                                .uri("/auth/v1/onboarding")
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(signUpRequest))
                                .retrieve()
                                .bodyToMono(CommonResponse.class)
                                .flatMap(authResponse ->{
                                    CommonResponse common = new CommonResponse();
                                    common.setMeta(authResponse.getMeta());
                                    common.setStatus(authResponse.getStatus());
                                    common.setData(authResponse.getData());
                                    return Mono.just(common);
                                });
                    } else {
                        // Handle unexpected principal type
                        log.error("Unexpected principal type: {}", user.getClass());
                        return Mono.error(new RuntimeException("Unexpected principal type"));
                    }
                })
                .onErrorResume(exception -> {
                    log.error("LOG::UserController saveOnboardingUsers user Save Exception " +
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
