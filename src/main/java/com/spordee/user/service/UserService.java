package com.spordee.user.service;

import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.response.common.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    CommonResponse saveOnboardingUsers(InitialUserSaveRequestDto initialUserSaveRequestDto, CommonResponse commonResponse, HttpServletResponse response);
}
