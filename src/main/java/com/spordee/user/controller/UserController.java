package com.spordee.user.controller;

import com.spordee.user.configurations.JwtFilter;
import com.spordee.user.configurations.JwtUtil;
import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.StatusType;
import com.spordee.user.response.common.CommonResponse;
import com.spordee.user.response.common.MetaData;
import com.spordee.user.service.UserService;
import com.spordee.user.util.JWTUtil;
import com.spordee.user.configurations.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.spordee.user.configurations.JwtUtil.extractAllClaims;
import static com.spordee.user.util.StatusCodes.CODE_CONFLICT;
import static com.spordee.user.util.StatusCodes.CODE_SUCCESS;

@RestController
@RequestMapping("${api.class.header}")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JwtFilter jwtFilter;

    private final JwtUtil jwtUtil;

    private final JWTUtil jwtUtils;


    @PostMapping("${api.class.method}")
    public CommonResponse saveOnboardingUsers(@RequestBody InitialUserSaveRequestDto initialUserSaveRequestDto, @RequestHeader("Authorization") String authorizationHeader,HttpServletResponse httpServletResponse){
        CommonResponse commonResponse  = new CommonResponse();
        PrimaryUserDetails  primaryUserDetails= userService.saveOnboardingUsers(initialUserSaveRequestDto);
        try{
            if(primaryUserDetails != null){
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                commonResponse.setData(primaryUserDetails);
                commonResponse.setStatus(StatusType.STATUS_SUCCESS);
                commonResponse.setMeta(new MetaData(false,CommonMessages.REQUEST_SUCCESS, CODE_SUCCESS.getCode(),"Saved Successfully"));


                //Decrypt the token
                String token  = jwtFilter.tokenDecryption(authorizationHeader.substring(7));
                System.out.println(token);
                System.out.println(extractAllClaims(token));
                System.out.println(JWTUtil.getHeaderFromToken(token));


                // generate the token

//                try{
//                    RestTemplate restTemplate = new RestTemplate();
//                    Map<String,Object> body = new HashMap<>();
//                    body.put("username", initialUserSaveRequestDto.getUserEmail());
//                    body.put("role", initialUserSaveRequestDto.getRegistrationType());
//                    CommonResponse tokenResponse = restTemplate.postForObject("http://localhost:8085/auth/v1/onboarding",body,CommonResponse.class);
//                    System.out.println(tokenResponse.getData());
//
//                }catch(Exception e){
//                    httpServletResponse.setStatus(HttpServletResponse.SC_CONFLICT);
//                    commonResponse.setData(e.getMessage());
//                    commonResponse.setStatus(StatusType.STATUS_FAIL);
//                    commonResponse.setMeta(new MetaData(true,CommonMessages.REQUEST_CONFLICT, CODE_CONFLICT.getCode(),"Failed."));
//                    return commonResponse;
//                }


                return commonResponse;
            }
        }catch(Exception e){
            httpServletResponse.setStatus(HttpServletResponse.SC_CONFLICT);
            commonResponse.setData(e.getMessage());
            commonResponse.setStatus(StatusType.STATUS_FAIL);
            commonResponse.setMeta(new MetaData(true,CommonMessages.REQUEST_CONFLICT, CODE_CONFLICT.getCode(),"Failed."));
            return commonResponse;
        }
        return commonResponse;

    }
}
