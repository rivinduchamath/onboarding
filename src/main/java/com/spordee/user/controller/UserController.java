package com.spordee.user.controller;

import com.spordee.user.configurations.Entity.SpordUser;
import com.spordee.user.configurations.Request.SignUpDto;
import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.dto.UpdateUserRequestDto;
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


    @PostMapping("${api.class.method}")
    public Mono<CommonResponse> saveOnboardingUsers(@RequestBody InitialUserSaveRequestDto initialUserSaveRequestDto,
                                                    Principal principal, @RequestHeader("Authorization") String token) {
        log.info("LOG::UserController saveOnboardingUsers");
        AtomicReference<String> deviceId = new AtomicReference<>("");
        AtomicReference<String> device = new AtomicReference<>("");
        // save data into database -> PimraryUserDetails
        Mono<CommonResponse> monoResponse =  userService.saveOnboardingUsers(initialUserSaveRequestDto,new CommonResponse(), principal.getName());

        if(monoResponse == null){
            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setMeta(new MetaData(true,CommonMessages.INTERNAL_SERVER_ERROR,500,"Issue is in save the details in database"));
            commonResponse.setData("There is an issue in saving the details in database");
            commonResponse.setStatus(StatusType.STATUS_FAIL);
            return Mono.just(commonResponse);
        }



        return Mono.justOrEmpty(principal)
                .flatMap(user -> {
                    if (user instanceof UsernamePasswordAuthenticationToken) {
                        SpordUser spordUser = (SpordUser) ((UsernamePasswordAuthenticationToken) user).getPrincipal();
                        deviceId.set(spordUser.getDeviceId());
                        device.set(spordUser.getDevice());
                        SignUpDto signUpRequest = SignUpDto.builder()
                                .providerId(principal.getName())
                                .device(Device.valueOf(device.get()))
                                .deviceId(deviceId.get())
                                .authProvider(((SpordUser) ((UsernamePasswordAuthenticationToken) user).getPrincipal()).getAuthProvider())
                                .role(initialUserSaveRequestDto.getRegistrationType())
                                .build();

                        return webClient.patch()
                                .uri("/auth/v1/onboarding")
                                .header("Authorization",token)
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
