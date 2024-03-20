package com.spordee.user.service;

import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;

public interface UserService {
    PrimaryUserDetails saveOnboardingUsers(InitialUserSaveRequestDto initialUserSaveRequestDto);
}
