package com.spordee.user.controller;

//import com.spordee.user.annotations.CurrentUser;
import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.StatusType;
import com.spordee.user.response.common.CommonResponse;
import com.spordee.user.response.common.MetaData;
import com.spordee.user.service.UserService;
import com.sun.security.auth.UserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.spordee.user.util.StatusCodes.CODE_CONFLICT;
import static com.spordee.user.util.StatusCodes.CODE_SUCCESS;

@RestController
@RequestMapping("${api.class.header}")
@Slf4j
@RequiredArgsConstructor
public class UserController {
private final UserService userService;
    @PostMapping("${api.class.method}")
    public CommonResponse saveOnboardingUsers(@RequestBody InitialUserSaveRequestDto initialUserSaveRequestDto, HttpServletResponse httpServletResponse){
        CommonResponse commonResponse  = new CommonResponse();
        PrimaryUserDetails  primaryUserDetails= userService.saveOnboardingUsers(initialUserSaveRequestDto);
        try{
            if(primaryUserDetails != null){
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                commonResponse.setData(primaryUserDetails);
                commonResponse.setStatus(StatusType.STATUS_SUCCESS);
                commonResponse.setMeta(new MetaData(false,CommonMessages.REQUEST_SUCCESS, CODE_SUCCESS.getCode(),"Saved Successfully"));
                return commonResponse;
            }else{
                httpServletResponse.setStatus(HttpServletResponse.SC_CONFLICT);
                commonResponse.setData("Null Found.");
                commonResponse.setStatus(StatusType.STATUS_FAIL);
                commonResponse.setMeta(new MetaData(true,CommonMessages.REQUEST_CONFLICT, CODE_CONFLICT.getCode(),"Null Found."));
                return commonResponse;
            }
        }catch(Exception e){
            httpServletResponse.setStatus(HttpServletResponse.SC_CONFLICT);
            commonResponse.setData(e.getMessage());
            commonResponse.setStatus(StatusType.STATUS_FAIL);
            commonResponse.setMeta(new MetaData(true,CommonMessages.REQUEST_CONFLICT, CODE_CONFLICT.getCode(),"Failed."));
            return commonResponse;
        }

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
    }
}
