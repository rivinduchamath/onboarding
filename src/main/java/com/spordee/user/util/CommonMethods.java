package com.spordee.user.util;

import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.dto.objects.UserImagesDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.entity.primaryUserData.cascadetables.UserImages;
import com.spordee.user.enums.UserStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.spordee.user.enums.RegistrationType.FAN;
import static com.spordee.user.enums.RegistrationType.PLAYER;

public  class CommonMethods {

    private CommonMethods(){

    }

    public static long getCurrentEpochTimeInSec(){
        return Instant.now().getEpochSecond();
    }


    public static List<UserImages> saveUserImagesFromDto(List<UserImagesDto> userImagesDtoList){
        List<UserImages> userImages = new ArrayList<>();
        long currentTime = getCurrentEpochTimeInSec();
        if(!userImagesDtoList.isEmpty()){
            for(UserImagesDto image : userImagesDtoList){
                UserImages userImage = UserImages.builder()
                        .isActive(image.isActive())
                        .imageUrl(image.getImageUrl())
                        .imageType(image.getImageType())
                        .description(image.getDescription())
                        .createdDate(String.valueOf(currentTime))
                        .build();
                userImages.add(userImage);
            }
        }
        return userImages;
    }

    public static PrimaryUserDetails savePrimaryUserDetailsFromDto(InitialUserSaveRequestDto initialUserSaveRequestDto){
        long currentTime = getCurrentEpochTimeInSec();
        List<UserImages> userImages = saveUserImagesFromDto(initialUserSaveRequestDto.getUserImagesDtos());
        PrimaryUserDetails primaryUserDetails = PrimaryUserDetails.builder()
                .firstName(initialUserSaveRequestDto.getFirstName())
                .lastName(initialUserSaveRequestDto.getLastName())
                .city(initialUserSaveRequestDto.getCity())
                .country(initialUserSaveRequestDto.getCountry())
                .height(initialUserSaveRequestDto.getHeight())
                .weight(initialUserSaveRequestDto.getWeight())
                .userStatus(UserStatus.ACTIVE)
                .createdDate(currentTime)
                .birthDay(initialUserSaveRequestDto.getBirthDay())
                .userEmail(initialUserSaveRequestDto.getUserEmail())
                .userImage(userImages)
                .roles(Collections.singletonList(initialUserSaveRequestDto.getRegistrationType().toString()))
                .build();

        if(initialUserSaveRequestDto.getRegistrationType().equals(PLAYER)){
            primaryUserDetails.setSport(initialUserSaveRequestDto.getUserSportsDtos());
            primaryUserDetails.setGender(initialUserSaveRequestDto.getGender());

        }else if(initialUserSaveRequestDto.getRegistrationType().equals(FAN)){
            // favourite -> Club team, player , all time player , national team
            primaryUserDetails.setFavPlayer(initialUserSaveRequestDto.getFavPlayer());
            primaryUserDetails.setFavSport(initialUserSaveRequestDto.getFavSport());
            primaryUserDetails.setSecondaryFavSports(initialUserSaveRequestDto.getSecondaryFavSports());
            primaryUserDetails.setFavClubTeam(initialUserSaveRequestDto.getFavClubTeam());
            primaryUserDetails.setFavNationalTeam(initialUserSaveRequestDto.getFavNationalTeam());
            primaryUserDetails.setFavAllTimePlayer(initialUserSaveRequestDto.getFavAllTimePlayer());

        }
        return primaryUserDetails;
    }

    public static Date getDateUTC(){
        // Get the current system time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        // Get the current UTC time in milliseconds
        long utcTimeMillis = currentTimeMillis - java.util.TimeZone.getDefault().getRawOffset();

        // Create a new Date object with the UTC time

        return new Date(utcTimeMillis);
    }

}
