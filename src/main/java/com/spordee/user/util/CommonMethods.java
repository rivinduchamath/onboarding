package com.spordee.user.util;

import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.dto.objects.UserImagesDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.entity.primaryUserData.cascadetables.UserImages;
import com.spordee.user.enums.UserStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.spordee.user.enums.RegistrationType.FAN;
import static com.spordee.user.enums.RegistrationType.PLAYER;

public class CommonMethods {

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
                .build();

        if(initialUserSaveRequestDto.getRegistrationType().equals(PLAYER)){
            primaryUserDetails.setSport(initialUserSaveRequestDto.getUserSportsDtos());
            primaryUserDetails.setGender(initialUserSaveRequestDto.getGender());
            primaryUserDetails.setUserStatus(UserStatus.ACTIVE);
            primaryUserDetails.setRoles(Collections.singletonList(initialUserSaveRequestDto.getRegistrationType().toString()));

        }else if(initialUserSaveRequestDto.getRegistrationType().equals(FAN)){
            // favourite -> Club team, player , all time player , national team
            primaryUserDetails.setFavSport(initialUserSaveRequestDto.getFavPlayer());
            primaryUserDetails.setFavClubTeam(initialUserSaveRequestDto.getFavClubTeam());
            primaryUserDetails.setFavNationalTeam(initialUserSaveRequestDto.getFavNationalTeam());
            primaryUserDetails.setFavAllTimePlayer(initialUserSaveRequestDto.getFavAllTimePlayer());

        }
        return primaryUserDetails;
    }

}
