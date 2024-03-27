package com.spordee.user.service.impl.update;

import com.spordee.user.dto.request.*;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.entity.profiledata.ProfileData;
import com.spordee.user.entity.sportsuserdata.UserSports;
import com.spordee.user.repository.PrimaryUserDataRepository;
import com.spordee.user.repository.ProfileDataRepository;
import com.spordee.user.repository.SportsRepository;
import com.spordee.user.dto.response.common.CommonResponse;
import com.spordee.user.service.UpdateUserService;
import com.spordee.user.util.CommonMethods;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.spordee.user.util.ResponseMethods.*;

@Service
@Slf4j
@AllArgsConstructor
public class UpdateUserServiceImpl implements UpdateUserService {
    private final PrimaryUserDataRepository primaryUserDataRepository;
    private final SportsRepository sportsRepository;
    private final ProfileDataRepository profileDataRepository;
    private final CommonMethods commonMethods;

    @Override
    public Mono<CommonResponse> updatePersonalDetails(PersonalInformationRequestDto updateUserRequestDto, CommonResponse commonResponse) {
        log.info("LOG:: UpdateUserServiceImpl updatePersonalDetails for username: {}", updateUserRequestDto.getUserName());
        try {
            PrimaryUserDetails byUserName = primaryUserDataRepository.findByUserName(updateUserRequestDto.getUserName());
        }catch (Exception e){
            e.printStackTrace();
        }

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
                                        return commonMethods.updatePrimaryAndProfileData(updateUserRequestDto,
                                                        primaryUserData, profileData, commonResponse)
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


    private Mono<ProfileData> fetchProfileDataAsync(String username) {
        return Mono.fromCallable(() -> profileDataRepository.findByUserName(username))
                .subscribeOn(Schedulers.boundedElastic());
    }
    @Override
    public Mono<CommonResponse> updateSpecs(SpecsInfoRequestDto updateUserRequestDto, CommonResponse commonResponse) {
        return Mono.defer(() -> {
            log.info("LOG:: UpdateUserServiceImpl updateSpecs for username: {}", updateUserRequestDto.getUserName());

            return Mono.fromCallable(() -> profileDataRepository.findByUserName(updateUserRequestDto.getUserName()))
                    // Cache primary user data (if applicable)
                    .map(profileData -> {
                        if (profileData != null) {
                            log.info("LOG:: UpdateUserServiceImpl updateSpecs profileData username: {}", profileData.getUserName());
                            return fetchSportDataAsync(profileData.getUserName())
                                    .map(userSports -> {
                                        if (userSports != null) {
                                            log.info("LOG:: userSports data found for username: {}", userSports.getUserName());
                                            // Use a locking mechanism to synchronize profile updates
                                            return commonMethods.updateSportsAndProfileData(updateUserRequestDto,
                                                            userSports, profileData, commonResponse)
                                                    .subscribeOn(Schedulers.boundedElastic()); // Bounded concurrency
                                        } else {
                                            log.info("LOG:: userSports data Not Found for username: {}", profileData.getUserName());
                                            return profileDataIsNull(commonResponse);
                                        }
                                    }).subscribeOn(Schedulers.boundedElastic());
                        } else {
                            log.info("LOG:: profileData Not Found for username: {}", updateUserRequestDto.getUserName());
                            return primaryUserDataIsNull(commonResponse);
                        }
                    })
                    .then(Mono.defer(() -> {
                        log.info("LOG:: user Sports data and primary user details updated successfully");
                        commonResponse.setData("Updated successfully");
                        return profileDataUpdateSuccess(commonResponse);
                    }))
                    .subscribeOn(Schedulers.boundedElastic()); // Bounded concurrency
    });
    }


    private Mono<UserSports> fetchSportDataAsync(String userName) {
        return Mono.fromCallable(() -> sportsRepository.findByUserName(userName))
                .subscribeOn(Schedulers.boundedElastic());
    }
    @Override
    public Mono<CommonResponse> updateSkillsAndSports(UpdateSkillsRequest updateSkillsRequest, CommonResponse commonResponse) {
        return Mono.defer(() -> {
            log.info("LOG:: UpdateUserServiceImpl updateSkillsAndSports for username: {}", updateSkillsRequest.getUserName());

            return Mono.fromCallable(() -> profileDataRepository.findByUserName(updateSkillsRequest.getUserName()))
                    // Cache primary user data (if applicable)
                    .map(profileData -> {
                        if (profileData != null) {
                            log.info("LOG:: UpdateUserServiceImpl updateSpecs profileData username: {}", profileData.getUserName());
                            return commonMethods.updateSkillsAndSports(updateSkillsRequest,
                                             profileData, commonResponse)
                                    .subscribeOn(Schedulers.boundedElastic());
                        } else {
                            log.info("LOG:: profileData Not Found for username: {}", updateSkillsRequest.getUserName());
                            return primaryUserDataIsNull(commonResponse);
                        }
                    })
                    .then(Mono.defer(() -> {
                        log.info("LOG:: user Sports data and primary user details updated successfully");
                        commonResponse.setData("Updated successfully");
                        return profileDataUpdateSuccess(commonResponse);
                    }))
                    .subscribeOn(Schedulers.boundedElastic()); // Bounded concurrency
        });
    }

    @Override
    public Mono<CommonResponse> updateAchievements(AchievementRequest achievementRequest, CommonResponse commonResponse) {
        return Mono.defer(() -> {
            log.info("LOG:: UpdateUserServiceImpl updateAchievements for username: {}", achievementRequest.getUserName());

            return Mono.fromCallable(() -> profileDataRepository.findByUserName(achievementRequest.getUserName()))
                    // Cache primary user data (if applicable)
                    .map(profileData -> {
                        if (profileData != null) {
                            log.info("LOG:: UpdateUserServiceImpl updateAchievements profileData username: {}", profileData.getUserName());
                            return commonMethods.updateAchievements(achievementRequest,
                                            profileData, commonResponse)
                                    .subscribeOn(Schedulers.boundedElastic());
                        } else {
                            log.info("LOG:: profileData Not Found for username: {}", achievementRequest.getUserName());
                            return primaryUserDataIsNull(commonResponse);
                        }
                    })
                    .then(Mono.defer(() -> {
                        log.info("LOG:: updateAchievements data and primary user details updated successfully");
                        commonResponse.setData("Updated successfully");
                        return profileDataUpdateSuccess(commonResponse);
                    }))
                    .subscribeOn(Schedulers.boundedElastic()); // Bounded concurrency
        });
    }

    @Override
    public Mono<CommonResponse> updateInstitution(InstitutionsRequestDto institutionsRequestDto, CommonResponse commonResponse) {
        return Mono.defer(() -> {
            log.info("LOG:: UpdateUserServiceImpl updateInstitution for username: {}", institutionsRequestDto.getUserName());

            return Mono.fromCallable(() -> profileDataRepository.findByUserName(institutionsRequestDto.getUserName()))
                    // Cache primary user data (if applicable)
                    .map(profileData -> {
                        if (profileData != null) {
                            log.info("LOG:: UpdateUserServiceImpl updateInstitution profileData username: {}", profileData.getUserName());
                            return commonMethods.updateInstitution(institutionsRequestDto,
                                            profileData, commonResponse)
                                    .subscribeOn(Schedulers.boundedElastic());
                        } else {
                            log.info("LOG:: profileData Not Found for username: {}", institutionsRequestDto.getUserName());
                            return primaryUserDataIsNull(commonResponse);
                        }
                    })
                    .then(Mono.defer(() -> {
                        log.info("LOG:: updateInstitution data and primary user details updated successfully");
                        commonResponse.setData("Updated successfully");
                        return profileDataUpdateSuccess(commonResponse);
                    }))
                    .subscribeOn(Schedulers.boundedElastic()); // Bounded concurrency
        });
    }

}