package com.spordee.user.service.impl;

import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.dto.objects.UserImagesDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.entity.primaryUserData.cascadetables.UserImages;
import com.spordee.user.enums.UserStatus;
import com.spordee.user.repository.PrimaryUserDataRepository;
import com.spordee.user.repository.UserImageRepository;
import com.spordee.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.spordee.user.enums.RegistrationType.FAN;
import static com.spordee.user.enums.RegistrationType.PLAYER;
import static com.spordee.user.util.CommonMethods.savePrimaryUserDetailsFromDto;
import static com.spordee.user.util.CommonMethods.saveUserImagesFromDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PrimaryUserDataRepository primaryUserDataRepository;

    private final UserImageRepository userImagesRepository;
    @Override
    public void saveOnboardingUsers(InitialUserSaveRequestDto initialUserSaveRequestDto) {
        PrimaryUserDetails primaryUserDetails = savePrimaryUserDetailsFromDto(initialUserSaveRequestDto);
        userImagesRepository.saveAll(primaryUserDetails.getUserImage());
        primaryUserDataRepository.save(primaryUserDetails);

    }
}
