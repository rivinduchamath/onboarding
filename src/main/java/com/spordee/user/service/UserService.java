package com.spordee.user.service;

import com.spordee.user.dto.InitialUserSaveRequestDto;

public interface UserService {
    void saveOnboardingUsers(InitialUserSaveRequestDto initialUserSaveRequestDto);
}
