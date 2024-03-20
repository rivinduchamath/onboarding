package com.spordee.user.controller;

import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.response.common.CommonResponse;
import com.spordee.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.class.header}")
@Slf4j
@RequiredArgsConstructor
public class UserController {
private final UserService userService;
    @PostMapping("${api.class.method}")
    public CommonResponse saveOnboardingUsers(@RequestBody InitialUserSaveRequestDto initialUserSaveRequestDto, HttpServletResponse httpServletResponse){
        userService.saveOnboardingUsers(initialUserSaveRequestDto);
        return null;
    }
}
