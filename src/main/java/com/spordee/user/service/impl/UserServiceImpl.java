package com.spordee.user.service.impl;

import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.repository.PrimaryUserDataRepository;
import com.spordee.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.spordee.user.enums.RegistrationType.FAN;
import static com.spordee.user.enums.RegistrationType.PLAYER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PrimaryUserDataRepository primaryUserDataRepository;
    @Override
    public void saveOnboardingUsers(InitialUserSaveRequestDto initialUserSaveRequestDto) {

        if(initialUserSaveRequestDto.getRegistrationType().equals(PLAYER)){

            PrimaryUserDetails primaryUserDetails = new PrimaryUserDetails();
//            primaryUserDetails.setUserName();

        }else if(initialUserSaveRequestDto.getRegistrationType().equals(FAN)){

        }
    }
}
