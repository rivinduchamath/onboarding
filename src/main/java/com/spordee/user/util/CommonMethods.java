package com.spordee.user.util;

import com.spordee.user.dto.request.InitialUserSaveRequestDto;
import com.spordee.user.dto.objects.UserImagesDto;
import com.spordee.user.dto.objects.UserSportsDto;
import com.spordee.user.dto.request.*;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.entity.primaryUserData.cascadetables.UserImages;
import com.spordee.user.entity.profiledata.ProfileData;
import com.spordee.user.entity.sportsuserdata.UserSports;
import com.spordee.user.enums.UserStatus;
import com.spordee.user.repository.PrimaryUserDataRepository;
import com.spordee.user.repository.ProfileDataRepository;
import com.spordee.user.repository.SportsRepository;
import com.spordee.user.dto.response.common.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.spordee.user.enums.RegistrationType.REGISTRATION_TYPE_FAN;
import static com.spordee.user.enums.RegistrationType.REGISTRATION_TYPE_PLAYER;
import static com.spordee.user.util.ResponseMethods.profileDataUpdateSuccess;

@Component
@Slf4j
@AllArgsConstructor
public  class CommonMethods {
    private final ProfileDataRepository profileDataRepository;
    private final PrimaryUserDataRepository primaryUserDataRepository;
    private final SportsRepository sportsRepository;
    public static long getCurrentEpochTimeInSec(){
        return Instant.now().getEpochSecond();
    }






    public static List<UserImages> saveUserImagesFromDto(List<UserImagesDto> userImagesDtoList, long currentTime) {
        if (!userImagesDtoList.isEmpty()) {
            return userImagesDtoList.stream()
                    .map(image -> UserImages.builder()
                            .isActive(image.isActive())
                            .imageUrl(image.getImageUrl())
                            .imageType(image.getImageType())
                            .description(image.getDescription())
                            .createdDate(currentTime)
                            .build())
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
    public static PrimaryUserDetails savePrimaryUserDetailsFromDto(InitialUserSaveRequestDto initialUserSaveRequestDto){
        long currentTime = getCurrentEpochTimeInSec();
        PrimaryUserDetails.PrimaryUserDetailsBuilder primaryUserDetails = PrimaryUserDetails.builder()
                .id(initialUserSaveRequestDto.getId())
                .userName(initialUserSaveRequestDto.getUserName())
                .name(initialUserSaveRequestDto.getFirstName())
                .name(initialUserSaveRequestDto.getLastName())
                .city(initialUserSaveRequestDto.getCity())
                .homeCountry(initialUserSaveRequestDto.getCountry())
                .userStatus(UserStatus.ACTIVE)
                .createdDate(currentTime)
                .birthDay(initialUserSaveRequestDto.getBirthDay())
                .userEmail(initialUserSaveRequestDto.getUserEmail())
                .countryCode(initialUserSaveRequestDto.getCountryCode())
                .userImage(saveUserImagesFromDto(initialUserSaveRequestDto.getUserImagesDtos(), currentTime))
                .roles(Collections.singletonList(initialUserSaveRequestDto.getRegistrationType().toString()));

        if(initialUserSaveRequestDto.getRegistrationType().equals(REGISTRATION_TYPE_PLAYER)){
//            primaryUserDetails.sport(initialUserSaveRequestDto.getUserSportsDtos())
                    primaryUserDetails.sportsGender(initialUserSaveRequestDto.getSportsGender());


        }else if(initialUserSaveRequestDto.getRegistrationType().equals(REGISTRATION_TYPE_FAN)){
            primaryUserDetails.favPlayer(initialUserSaveRequestDto.getFavPlayer())
                    .favSport(initialUserSaveRequestDto.getFavSport())
                    .secondaryFavSports(initialUserSaveRequestDto.getSecondaryFavSports())
                    .favClubTeam(initialUserSaveRequestDto.getFavClubTeam())
                    .favNationalTeam(initialUserSaveRequestDto.getFavNationalTeam())
                    .favAllTimePlayer(initialUserSaveRequestDto.getFavAllTimePlayer());
        }
        return primaryUserDetails.build();
    }

    public static ProfileData saveProfileDataFromDto(InitialUserSaveRequestDto initialUserSaveRequestDto){
        return  ProfileData.builder()
                .userName(initialUserSaveRequestDto.getUserName())
                .name(initialUserSaveRequestDto.getFirstName())
                .birthCountry(initialUserSaveRequestDto.getBirthCountry())
                .birthCountry(initialUserSaveRequestDto.getBirthCity())
                .weight(initialUserSaveRequestDto.getWeight())
                .height(initialUserSaveRequestDto.getHeight())
                .createdDate(getCurrentEpochTimeInSec())
                .build();

    }
    public Mono<CommonResponse> updatePrimaryAndProfileData(PersonalInformationRequestDto updateUserRequestDto, PrimaryUserDetails primaryUserData, ProfileData profileData, CommonResponse commonResponse) {

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
    public static void updateProfileDataConditionally(PersonalInformationRequestDto updateUserRequestDto, PrimaryUserDetails primaryUserData) {
        Optional.ofNullable(updateUserRequestDto.getName()).ifPresent(primaryUserData::setName);
        Optional.ofNullable(updateUserRequestDto.getUserName()).ifPresent(primaryUserData::setUserName);
        Optional.of(updateUserRequestDto.getBirthDay()).ifPresent(primaryUserData::setBirthDay);
        Optional.ofNullable(updateUserRequestDto.getUserEmail()).ifPresent(primaryUserData::setUserEmail);
        Optional.ofNullable(updateUserRequestDto.getPhoneNumber()).ifPresent(primaryUserData::setPhoneNumber);
        Optional.ofNullable(updateUserRequestDto.getHomeCountry()).ifPresent(primaryUserData::setHomeCountry);
        Optional.ofNullable(updateUserRequestDto.getLanguages()).ifPresent(primaryUserData::setLanguages);
    }
    public static void updateUserDetailsConditionally(PersonalInformationRequestDto updateUserRequestDto, ProfileData profileData) {
        Optional.ofNullable(updateUserRequestDto.getPlaceOfResidence()).ifPresent(profileData::setPlaceOfResidence);
        Optional.ofNullable(updateUserRequestDto.getCitizenship()).ifPresent(profileData::setCitizenShip);
        Optional.ofNullable(updateUserRequestDto.getPlaceOfBirth()).ifPresent(profileData::setPlaceOfBirth);
    }
    private UserSports saveSportsUserData(UserSports primaryUserData) {
        // Simulate saving PrimaryUserDetails asynchronously
        return sportsRepository.save(primaryUserData);
    }
    public static UserSports changeAndUpdateToUserSports(UserSportsDto userSportsDto,UserSports userSports){
        Optional.ofNullable(userSportsDto.getAmericanFootball()).ifPresent(userSports::setAmericanFootball);
        Optional.ofNullable(userSportsDto.getBaseball()).ifPresent(userSports::setBaseball);
        Optional.ofNullable(userSportsDto.getBasketball()).ifPresent(userSports::setBasketball);
        Optional.ofNullable(userSportsDto.getIceHockey()).ifPresent(userSports::setHockey);
        Optional.ofNullable(userSportsDto.getCricket()).ifPresent(userSports::setCricket);
        Optional.ofNullable(userSportsDto.getRugby()).ifPresent(userSports::setRugby);
        Optional.ofNullable(userSportsDto.getSoccer()).ifPresent(userSports::setSoccer);
        userSports.setUpdatedDate(String.valueOf(getCurrentEpochTimeInSec()));
        return userSports;
    }
    public Mono<CommonResponse> updateSportsAndProfileData(SpecsInfoRequestDto updateUserRequestDto, UserSports userSports, ProfileData profileData, CommonResponse commonResponse) {
        ProfileData profileData1 = updateProfileDataSpecsConditionally(updateUserRequestDto, profileData);
        UserSports userSports1 = changeAndUpdateToUserSports(updateUserRequestDto.getUserSportsDto(), userSports);

        return Mono.defer(() ->
                        Mono.fromCallable(() -> {
                            // Save the updated data asynchronously on a separate scheduler
                            UserSports saveSportsUserData = saveSportsUserData(userSports1);
                            ProfileData savedProfileData = saveProfileData(profileData1);

                            // Return the saved data
                            return Mono.just(Tuples.of(saveSportsUserData, savedProfileData));
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

    private ProfileData updateProfileDataSpecsConditionally(SpecsInfoRequestDto updateUserRequestDto, ProfileData profileData) {
        Optional.ofNullable(updateUserRequestDto.getHeight()).ifPresent(profileData::setHeight);
        Optional.ofNullable(updateUserRequestDto.getWeight()).ifPresent(profileData::setWeight);
        return profileData;
    }

    public Mono<CommonResponse> updateSkillsAndSports(UpdateSkillsRequest updateSkillsRequest, ProfileData profileData, CommonResponse commonResponse) {
        ProfileData profileData1 = updateProfileDataSkillsConditionally(updateSkillsRequest, profileData);

        return Mono.defer(() ->
                        Mono.fromCallable(() -> {
                            // Save the updated data asynchronously on a separate scheduler
                            ProfileData savedProfileData = saveProfileData(profileData1);
                            // Return the saved data
                            return Mono.just(savedProfileData);
                        })
                )
                .subscribeOn(Schedulers.boundedElastic()) // Execute the blocking call on a separate scheduler
                .flatMap(savedDataMono -> savedDataMono)
                .flatMap(savedData -> {
                    log.info("LOG:: Updating primary user details Skills and profile data completed");
                    commonResponse.setData(savedData);
                    return profileDataUpdateSuccess(commonResponse);
                });
    }

    private ProfileData updateProfileDataSkillsConditionally(UpdateSkillsRequest updateSkillsRequest, ProfileData profileData) {
        Optional.ofNullable(updateSkillsRequest.getSkills()).ifPresent(profileData::setSkills);
        return profileData;
    }

    public Mono<CommonResponse> updateAchievements(AchievementRequest achievementRequest, ProfileData profileData, CommonResponse commonResponse) {
        ProfileData profileData1 = updateAchievementsConditionally(achievementRequest, profileData);

        return Mono.defer(() ->
                        Mono.fromCallable(() -> {
                            // Save the updated data asynchronously on a separate scheduler
                            ProfileData savedProfileData = saveProfileData(profileData1);
                            // Return the saved data
                            return Mono.just(savedProfileData);
                        })
                )
                .subscribeOn(Schedulers.boundedElastic()) // Execute the blocking call on a separate scheduler
                .flatMap(savedDataMono -> savedDataMono)
                .flatMap(savedData -> {
                    log.info("LOG:: Updating updateAchievements details Skills and profile data completed");
                    commonResponse.setData(savedData);
                    return profileDataUpdateSuccess(commonResponse);
                });
    }

    private ProfileData updateAchievementsConditionally(AchievementRequest achievementRequest, ProfileData profileData) {
        Optional.ofNullable(achievementRequest.getAchievements()).ifPresent(profileData::setAchievements);
        return profileData;
    }

    public Mono<CommonResponse> updateInstitution(InstitutionsRequestDto institutionsRequestDto, ProfileData profileData, CommonResponse commonResponse) {
        ProfileData profileData1 = updateInstitutionConditionally(institutionsRequestDto, profileData);

        return Mono.defer(() ->
                        Mono.fromCallable(() -> {
                            // Save the updated data asynchronously on a separate scheduler
                            ProfileData savedProfileData = saveProfileData(profileData1);
                            // Return the saved data
                            return Mono.just(savedProfileData);
                        })
                )
                .subscribeOn(Schedulers.boundedElastic()) // Execute the blocking call on a separate scheduler
                .flatMap(savedDataMono -> savedDataMono)
                .flatMap(savedData -> {
                    log.info("LOG:: Updating updateInstitution details Skills and profile data completed");
                    commonResponse.setData(savedData);
                    return profileDataUpdateSuccess(commonResponse);
                });
    }

    private ProfileData updateInstitutionConditionally(InstitutionsRequestDto institutionsRequestDto, ProfileData profileData) {
        Optional.ofNullable(institutionsRequestDto.getInstituteDetails()).ifPresent(profileData::setInstituteDetails);
        return profileData;
    }


}
