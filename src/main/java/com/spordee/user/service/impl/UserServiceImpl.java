package com.spordee.user.service.impl;


import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.entity.sportsuserdata.UserSports;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.StatusType;
import com.spordee.user.repository.PrimaryUserDataRepository;
import com.spordee.user.repository.SportsRepository;
import com.spordee.user.response.common.CommonResponse;
import com.spordee.user.response.common.MetaData;
import com.spordee.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.spordee.user.exceptions.GlobalExceptionHandler.handleExceptionRootReactive;
import static com.spordee.user.util.CommonMethods.*;
import static com.spordee.user.util.StatusCodes.CODE_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PrimaryUserDataRepository primaryUserDataRepository;
    private final SportsRepository sportsRepository;


    @Override
    public Mono<CommonResponse> saveOnboardingUsers(InitialUserSaveRequestDto initialUserSaveRequestDto,
                                                    CommonResponse commonResponse) {
        log.info("LOG:: UserServiceImpl saveOnboardingUsers");
        PrimaryUserDetails primaryUserDetails = savePrimaryUserDetailsFromDto(initialUserSaveRequestDto);
        if(initialUserSaveRequestDto.getUserSportsDtos() !=null){
            UserSports userSports =  UserSports.builder()
                    .rugby(initialUserSaveRequestDto.getUserSportsDtos().getRugby())
                    .baseball(initialUserSaveRequestDto.getUserSportsDtos().getBaseball())
                    .americanFootball(initialUserSaveRequestDto.getUserSportsDtos().getAmericanFootball())
                    .hockey(initialUserSaveRequestDto.getUserSportsDtos().getIceHockey())
                    .soccer(initialUserSaveRequestDto.getUserSportsDtos().getSoccer())
                    .cricket(initialUserSaveRequestDto.getUserSportsDtos().getCricket())
                    .basketball(initialUserSaveRequestDto.getUserSportsDtos().getBasketball())
                    .createdDate(String.valueOf(getCurrentEpochTimeInSec()))
                    .build();
            sportsRepository.save(userSports);
        }
        PrimaryUserDetails save = primaryUserDataRepository.save(primaryUserDetails);
        return Mono.just(save).map(savedPrimaryUserDetails -> {
            commonResponse.setData(savedPrimaryUserDetails);
            commonResponse.setStatus(StatusType.STATUS_SUCCESS);
            commonResponse.setMeta(new MetaData(false, CommonMessages.REQUEST_SUCCESS, CODE_SUCCESS.getCode(),
                    "Saved Successfully"));
            log.debug("LOG:: UserServiceImpl saveOnboardingUsers {} save Success ",
                    savedPrimaryUserDetails.getUserName());

            return commonResponse;
        }).onErrorResume(exception -> handleExceptionRootReactive(
                CommonMessages.INTERNAL_SERVER_ERROR,
                StatusType.STATUS_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception,
                commonResponse,
                "Error In Internal Server"
                ));
    }


}
