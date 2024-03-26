package com.spordee.user.service.impl.update;

import com.spordee.user.dto.UpdateUserRequestDto;
import com.spordee.user.dto.request.PersonalInformationRequestDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.entity.profiledata.ProfileData;
import com.spordee.user.entity.sportsuserdata.UserSports;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.StatusType;
import com.spordee.user.repository.PrimaryUserDataRepository;
import com.spordee.user.repository.ProfileDataRepository;
import com.spordee.user.repository.SportsRepository;
import com.spordee.user.response.common.CommonResponse;
import com.spordee.user.response.common.MetaData;
import com.spordee.user.service.UpdateUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.spordee.user.exceptions.GlobalExceptionHandler.handleExceptionRootReactive;
import static com.spordee.user.util.CommonMethods.*;
import static com.spordee.user.util.ResponseMethods.*;

@Service
@Slf4j
@AllArgsConstructor
public class UpdateUserServiceImpl implements UpdateUserService {
    private final PrimaryUserDataRepository primaryUserDataRepository;
    private final SportsRepository sportsRepository;
    private final ProfileDataRepository profileDataRepository;

    @Override
    public Mono<CommonResponse> updatePersonalDetails(PersonalInformationRequestDto updateUserRequestDto, CommonResponse commonResponse) {
        log.info("LOG:: UpdateUserServiceImpl updatePersonalDetails for username: {}", updateUserRequestDto.getUserName());

        return Mono.fromCallable(() -> primaryUserDataRepository.findByUserName(updateUserRequestDto.getUserName()))
                // Cache primary user data (if applicable)
                .flatMap(primaryUserData -> {
                    if (primaryUserData != null) {
                        log.info("LOG:: Primary User data found for username: {}", primaryUserData.getUserName());
                        return fetchProfileDataAsync(primaryUserData.getUserName())
                                .flatMap(profileData -> {
                                    if (profileData != null) {
                                        log.info("LOG:: Profile data found for username: {}", profileData.getUserName());
                                        // Use a locking mechanism to synchronize profile updates
                                        return updatePrimaryAndProfileData(updateUserRequestDto, primaryUserData, profileData, commonResponse)
                                                .subscribeOn(Schedulers.boundedElastic()); // Bounded concurrency
                                    } else {
                                        log.info("LOG:: Profile data Not Found for username: {}", primaryUserData.getUserName());
                                        return profileDataIsNull(commonResponse);
                                    }
                                }).subscribeOn(Schedulers.boundedElastic());
                    } else {
                        log.info("LOG:: Primary User Not Found for username: {}", updateUserRequestDto.getUserName());
                        return primaryUserDataIsNull(commonResponse);
                    }
                })
                .then(Mono.defer(() -> {
                    log.info("LOG:: Profile data and primary user details updated successfully");
                    commonResponse.setData("Updated successfully");
                    return profileDataUpdateSuccess(commonResponse);
                }))
                .subscribeOn(Schedulers.boundedElastic()); // Bounded concurrency
    }

    private Mono<CommonResponse> updatePrimaryAndProfileData(PersonalInformationRequestDto updateUserRequestDto, PrimaryUserDetails primaryUserData, ProfileData profileData, CommonResponse commonResponse) {

        updateProfileDataConditionally(updateUserRequestDto, primaryUserData);
        updateUserDetailsConditionally(updateUserRequestDto, profileData);

        return Mono.defer(() ->
                        Mono.fromCallable(() -> {
                            // Save the updated data asynchronously on a separate scheduler
                            PrimaryUserDetails savedPrimaryUserData = savePrimaryUserData(primaryUserData);
                            ProfileData savedProfileData = saveProfileData(profileData);

                            // Return the saved data
                            return Mono.just(Tuples.of(savedPrimaryUserData, savedProfileData));
                        })
                )
                .subscribeOn(Schedulers.boundedElastic()) // Execute the blocking call on a separate scheduler
                .flatMap(savedDataMono -> savedDataMono)
                .flatMap(savedData -> {
                    log.info("LOG:: Updating primary user details and profile data completed");
                    commonResponse.setData(savedData);
                    return profileDataUpdateSuccess(commonResponse);
                });
    }



    private PrimaryUserDetails savePrimaryUserData(PrimaryUserDetails primaryUserData) {
        // Simulate saving PrimaryUserDetails asynchronously
        return primaryUserDataRepository.save(primaryUserData);
    }

    private ProfileData saveProfileData(ProfileData profileData) {
        // Simulate saving ProfileData asynchronously
        return profileDataRepository.save(profileData);
    }
    private Mono<ProfileData> fetchProfileDataAsync(String username) {
        return Mono.fromCallable(() -> profileDataRepository.findByUserName(username))
                .subscribeOn(Schedulers.boundedElastic());
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
