package com.spordee.user.util;

import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.dto.UpdateUserRequestDto;
import com.spordee.user.dto.objects.UserImagesDto;
import com.spordee.user.dto.objects.UserSportsDto;
import com.spordee.user.dto.request.PersonalInformationRequestDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.entity.primaryUserData.cascadetables.UserImages;
import com.spordee.user.entity.profiledata.ProfileData;
import com.spordee.user.entity.sportsuserdata.UserSports;
import com.spordee.user.enums.UserStatus;
import com.spordee.user.exceptions.OAuth2AuthenticationProcessingException;
import io.micrometer.observation.ObservationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.spordee.user.enums.RegistrationType.REGISTRATION_TYPE_FAN;
import static com.spordee.user.enums.RegistrationType.REGISTRATION_TYPE_PLAYER;

@Component
public  class CommonMethods {

    private CommonMethods(){

    }

    @Value("${jwt.tokenDecryptCode}")
    private  String tokenDecryptCode;
    public static long getCurrentEpochTimeInSec(){
        return Instant.now().getEpochSecond();
    }



    public static UserSports changeAndUpdateToUserSports(UserSportsDto userSportsDto,UserSports userSports){
        userSports.setAmericanFootball(userSportsDto.getAmericanFootball());
        userSports.setBaseball(userSportsDto.getBaseball());
        userSports.setBasketball(userSportsDto.getBasketball());
        userSports.setHockey(userSportsDto.getIceHockey());
        userSports.setCricket(userSportsDto.getCricket());
        userSports.setRugby(userSportsDto.getRugby());
        userSports.setSoccer(userSportsDto.getSoccer());
        userSports.setUpdatedDate(String.valueOf(getCurrentEpochTimeInSec()));
        return userSports;
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
    public static PrimaryUserDetails updatePersonal(UpdateUserRequestDto updateUserRequestDto, PrimaryUserDetails primaryUserDetails){
        primaryUserDetails.setUserEmail(StringUtils.hasText(updateUserRequestDto.getEmail()) ? updateUserRequestDto.getEmail() : primaryUserDetails.getUserEmail());
        primaryUserDetails.setName(StringUtils.hasText(updateUserRequestDto.getName()) ? updateUserRequestDto.getName() : primaryUserDetails.getName());
        primaryUserDetails.setBirthDay(Long.valueOf(updateUserRequestDto.getDateOfBirth()) != null ? updateUserRequestDto.getDateOfBirth() : primaryUserDetails.getBirthDay());
        primaryUserDetails.setPhoneNumber(StringUtils.hasText(updateUserRequestDto.getPhoneNumber()) ? updateUserRequestDto.getPhoneNumber() : primaryUserDetails.getPhoneNumber());
        primaryUserDetails.setName(StringUtils.hasText(updateUserRequestDto.getHomeCountry()) ? updateUserRequestDto.getHomeCountry() : primaryUserDetails.getName());
        primaryUserDetails.setLanguages(!updateUserRequestDto.getLanguages().isEmpty() ? updateUserRequestDto.getLanguages() : primaryUserDetails.getLanguages());
        primaryUserDetails.setUpdatedDate(getCurrentEpochTimeInSec());
        return primaryUserDetails;
    }

    public static ProfileData updatePersonalData(UpdateUserRequestDto updateUserRequestDto, ProfileData profileData){
        profileData.setEmail(StringUtils.hasText(updateUserRequestDto.getEmail()) ? updateUserRequestDto.getEmail() : profileData.getEmail());
        profileData.setName(StringUtils.hasText(updateUserRequestDto.getName()) ? updateUserRequestDto.getName() : profileData.getName());
        profileData.setBirthDay(Long.valueOf(updateUserRequestDto.getDateOfBirth()) != null ? String.valueOf(updateUserRequestDto.getDateOfBirth()) : profileData.getBirthDay());
        profileData.setPhoneNumber(StringUtils.hasText(updateUserRequestDto.getPhoneNumber()) ? updateUserRequestDto.getPhoneNumber() : profileData.getPhoneNumber());
        profileData.setCitizenShip(StringUtils.hasText(updateUserRequestDto.getCitizenship()) ? updateUserRequestDto.getCitizenship() : profileData.getCitizenShip());
        profileData.setCountryOfResidence(StringUtils.hasText(updateUserRequestDto.getHomeCountry()) ? updateUserRequestDto.getHomeCountry() : profileData.getCountryOfResidence());
        profileData.setBirthCountry(StringUtils.hasText(updateUserRequestDto.getCountryOfBirth()) ? updateUserRequestDto.getCountryOfBirth() : profileData.getBirthCountry());
        profileData.setBirthCountry(StringUtils.hasText(updateUserRequestDto.getCityOfBirth()) ? updateUserRequestDto.getCityOfBirth() : profileData.getBirthCountry());
        profileData.setBirthCountry(StringUtils.hasText(updateUserRequestDto.getCityOfResidence()) ? updateUserRequestDto.getCityOfResidence() : profileData.getBirthCountry());
        profileData.setCountryOfResidence(StringUtils.hasText(updateUserRequestDto.getCountryOfResidence()) ? updateUserRequestDto.getEmail() : profileData.getCountryOfResidence());
        profileData.setLanguages(!updateUserRequestDto.getLanguages().isEmpty() ? updateUserRequestDto.getLanguages() : profileData.getLanguages());
        profileData.setUpdatedDate(getCurrentEpochTimeInSec());
        return profileData;
    }


}
