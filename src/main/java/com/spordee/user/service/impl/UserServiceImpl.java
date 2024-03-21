package com.spordee.user.service.impl;

import com.spordee.user.configurations.JwtFilter;
import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.StatusType;
import com.spordee.user.repository.PrimaryUserDataRepository;
import com.spordee.user.repository.UserImageRepository;
import com.spordee.user.response.common.CommonResponse;
import com.spordee.user.response.common.MetaData;
import com.spordee.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.spordee.user.enums.CommonMessages.INTERNAL_SERVER_ERROR;
import static com.spordee.user.enums.StatusType.STATUS_FAIL;
import static com.spordee.user.exceptions.GlobalExceptionHandler.handleExceptionRoot;
import static com.spordee.user.util.CommonMethods.savePrimaryUserDetailsFromDto;
import static com.spordee.user.util.StatusCodes.CODE_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PrimaryUserDataRepository primaryUserDataRepository;


    @Override
    public CommonResponse saveOnboardingUsers(InitialUserSaveRequestDto initialUserSaveRequestDto,
                                              CommonResponse commonResponse, HttpServletResponse response) {
        log.info("LOG:: UserServiceImpl saveOnboardingUsers");
        try {
            PrimaryUserDetails primaryUserDetails = savePrimaryUserDetailsFromDto(initialUserSaveRequestDto);
            commonResponse.setData(primaryUserDataRepository.insert(primaryUserDetails));
            commonResponse.setStatus(StatusType.STATUS_SUCCESS);
            commonResponse.setMeta(new MetaData(false, CommonMessages.REQUEST_SUCCESS, CODE_SUCCESS.getCode(),
                    "Saved Successfully"));
            log.debug("LOG:: UserServiceImpl saveOnboardingUsers {} save Success ", primaryUserDetails.getUserName());
            return commonResponse;
        }catch (Exception exception){
            log.error("LOG::UserServiceImpl saveOnboardingUsers Exception");
           return handleExceptionRoot(INTERNAL_SERVER_ERROR,
                   STATUS_FAIL,
                   response,
                   HttpStatus.INTERNAL_SERVER_ERROR,
                   exception,
                   commonResponse,
                   "Error In Server" );
        }
    }
}
