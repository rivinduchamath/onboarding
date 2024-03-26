package com.spordee.user.service.impl;


import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.dto.UpdateUserRequestDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.entity.profiledata.ProfileData;
import com.spordee.user.entity.sportsuserdata.UserSports;
import com.spordee.user.entity.sportsuserdata.cascadetables.sports.*;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.RegistrationType;
import com.spordee.user.enums.StatusType;
import com.spordee.user.repository.PrimaryUserDataRepository;
import com.spordee.user.repository.ProfileDataRepository;
import com.spordee.user.repository.SportsRepository;
import com.spordee.user.response.common.CommonResponse;
import com.spordee.user.response.common.MetaData;
import com.spordee.user.service.UserService;
import io.micrometer.observation.ObservationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.spordee.user.exceptions.GlobalExceptionHandler.handleExceptionRootReactive;
import static com.spordee.user.util.CommonMethods.*;
import static com.spordee.user.util.StatusCodes.CODE_INTERNAL_SERVER_ERROR;
import static com.spordee.user.util.StatusCodes.CODE_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PrimaryUserDataRepository primaryUserDataRepository;
    private final SportsRepository sportsRepository;
    private final ProfileDataRepository profileDataRepository;


    @Override
    public Mono<CommonResponse> saveOnboardingUsers(InitialUserSaveRequestDto initialUserSaveRequestDto,
                                                    CommonResponse commonResponse,String username) {
        log.info("LOG:: UserServiceImpl saveOnboardingUsers");
        PrimaryUserDetails primaryUserDetails = savePrimaryUserDetailsFromDto(initialUserSaveRequestDto);
        ProfileData profileData = saveProfileDataFromDto(initialUserSaveRequestDto);
        if(initialUserSaveRequestDto.getUserSportsDtos() !=null && initialUserSaveRequestDto.getRegistrationType().equals(RegistrationType.REGISTRATION_TYPE_PLAYER)){
            UserSports userSports = UserSports.builder()
                            .userName(username)
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
        }).onErrorResume(exception -> handleExceptionRootReactive(
                CommonMessages.INTERNAL_SERVER_ERROR,
                StatusType.STATUS_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception,
                commonResponse,
                "Error In Internal Server"
                ));
    }

    @Override
    public Mono<CommonResponse> updatePersonalDetails(UpdateUserRequestDto updateUserRequestDto,String username,CommonResponse commonResponse) {
        PrimaryUserDetails primaryUserDetails = primaryUserDataRepository.findByUserName(username);
        ProfileData profileData = profileDataRepository.findByUserName(username);

        if(primaryUserDetails == null){
            commonResponse.setData("Data is not available in PrimaryUserDetails");
            commonResponse.setMeta(new MetaData(true,CommonMessages.INTERNAL_SERVER_ERROR,404,"Data is not available"));
            commonResponse.setStatus(StatusType.STATUS_FAIL);
            return Mono.just(commonResponse);
        }

        if(profileData == null){
            commonResponse.setData("Data is not available in ProfileUserData");
            commonResponse.setMeta(new MetaData(true,CommonMessages.INTERNAL_SERVER_ERROR,404,"Data is not available"));
            commonResponse.setStatus(StatusType.STATUS_FAIL);
            return Mono.just(commonResponse);
        }

        log.info("updating the primaryUserDetails and profileData");

        updatePersonal(updateUserRequestDto,primaryUserDetails);
        updatePersonalData(updateUserRequestDto,profileData);

        log.info("profile data and primary user details updated");

        commonResponse.setStatus(StatusType.STATUS_SUCCESS);
        commonResponse.setData("Updated successfully");
        commonResponse.setMeta(new MetaData(true,CommonMessages.REQUEST_SUCCESS,200,"Updated Successfully"));
        return Mono.just(commonResponse);
    }

    @Override
    public Mono<CommonResponse> updateHeightWeightAndSports(UpdateUserRequestDto updateUserRequestDto, String username, CommonResponse commonResponse) {
        UserSports userSports = sportsRepository.findByUserName(username);
        userSports = changeAndUpdateToUserSports(updateUserRequestDto.getUserSportsDto(),userSports);
        sportsRepository.save(userSports);
        log.info("UserSports saved.");
        ProfileData profileData = profileDataRepository.findByUserName(username);
        profileData.setWeight(updateUserRequestDto.getWeight());
        profileData.setHeight(updateUserRequestDto.getHeight());
        ProfileData save = profileDataRepository.save(profileData);
        log.info("profile data is updated {}",profileData.getUserName());

        return Mono.justOrEmpty(save)
                .flatMap(updateProfile->{
                    commonResponse.setData(updateProfile);
                    commonResponse.setMeta(new MetaData(false,CommonMessages.REQUEST_SUCCESS,200,"Profile Updated Successfully"));
                    commonResponse.setStatus(StatusType.STATUS_SUCCESS);
                    return Mono.just(commonResponse);
                }).onErrorResume(exception ->handleExceptionRootReactive(
                        CommonMessages.INTERNAL_SERVER_ERROR,
                        StatusType.STATUS_FAIL,
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        exception,
                        commonResponse,
                        "Error In Internal Server"
                ));

    }

    @Override
    public Mono<CommonResponse> updateSkills(UpdateUserRequestDto updateUserRequestDto, String username, CommonResponse commonResponse) {
        ProfileData profileData = profileDataRepository.findByUserName(username);
        profileData.setSkills(updateUserRequestDto.getSkills());
        ProfileData save = profileDataRepository.save(profileData);

        return Mono.justOrEmpty(save)
                .flatMap(updateProfile->{
                    commonResponse.setData(updateProfile);
                    commonResponse.setMeta(new MetaData(false,CommonMessages.REQUEST_SUCCESS,200,"Profile Updated Successfully"));
                    commonResponse.setStatus(StatusType.STATUS_SUCCESS);
                    return Mono.just(commonResponse);
                }).onErrorResume(exception ->handleExceptionRootReactive(
                        CommonMessages.INTERNAL_SERVER_ERROR,
                        StatusType.STATUS_FAIL,
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        exception,
                        commonResponse,
                        "Error In Internal Server"
                ));


    }


}
