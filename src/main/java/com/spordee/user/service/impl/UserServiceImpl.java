package com.spordee.user.service.impl;


import com.spordee.user.dto.request.InitialUserSaveRequestDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.entity.profiledata.ProfileData;
import com.spordee.user.entity.sportsuserdata.UserSports;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.RegistrationType;
import com.spordee.user.enums.StatusType;
import com.spordee.user.repository.PrimaryUserDataRepository;
import com.spordee.user.repository.ProfileDataRepository;
import com.spordee.user.repository.SportsRepository;
import com.spordee.user.dto.response.common.CommonResponse;
import com.spordee.user.dto.response.common.MetaData;
import com.spordee.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.spordee.user.util.CommonMethods.*;
import static com.spordee.user.util.ResponseMethods.internalServerError;
import static com.spordee.user.enums.StatusCodes.CODE_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PrimaryUserDataRepository primaryUserDataRepository;
    private final SportsRepository sportsRepository;
    private final ProfileDataRepository profileDataRepository;


    @Override
    public Mono<CommonResponse> saveOnboardingUsers(InitialUserSaveRequestDto initialUserSaveRequestDto,
                                                    CommonResponse commonResponse) {
        log.info("LOG:: UserServiceImpl saveOnboardingUsers");
        PrimaryUserDetails primaryUserDetails = savePrimaryUserDetailsFromDto(initialUserSaveRequestDto);
        ProfileData profileData = saveProfileDataFromDto(initialUserSaveRequestDto);
        if(initialUserSaveRequestDto.getUserSportsDtos() !=null && initialUserSaveRequestDto.getRegistrationType().equals(RegistrationType.REGISTRATION_TYPE_PLAYER)){
            UserSports userSports = UserSports.builder()
                            .userName(initialUserSaveRequestDto.getUserName())
                            .soccer(initialUserSaveRequestDto.getUserSportsDtos().getSoccer())
                            .americanFootball(initialUserSaveRequestDto.getUserSportsDtos().getAmericanFootball())
                            .rugby(initialUserSaveRequestDto.getUserSportsDtos().getRugby())
                            .baseball(initialUserSaveRequestDto.getUserSportsDtos().getBaseball())
                            .cricket(initialUserSaveRequestDto.getUserSportsDtos().getCricket())
                            .hockey(initialUserSaveRequestDto.getUserSportsDtos().getIceHockey())
                            .basketball(initialUserSaveRequestDto.getUserSportsDtos().getBasketball())
                            .build();

            UserSports sports =  sportsRepository.save(userSports);
            primaryUserDetails.setUserSports(sports);
        }
        ProfileData saveProfile = profileDataRepository.save(profileData);
        log.info("ProfileData is saved {}",saveProfile);
        PrimaryUserDetails save = primaryUserDataRepository.save(primaryUserDetails);
        return Mono.just(save).map(savedPrimaryUserDetails -> {
            commonResponse.setData(savedPrimaryUserDetails);
            commonResponse.setStatus(StatusType.STATUS_SUCCESS);
            commonResponse.setMeta(new MetaData(false, CommonMessages.REQUEST_SUCCESS, CODE_SUCCESS.getCode(),
                    "Saved Successfully"));
            log.debug("LOG:: UserServiceImpl saveOnboardingUsers {} save Success ",
                    savedPrimaryUserDetails.getUserName());

            return commonResponse;
        }).onErrorResume(exception -> internalServerError(
                commonResponse
                ));
    }



}
