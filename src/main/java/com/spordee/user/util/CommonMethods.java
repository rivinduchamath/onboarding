package com.spordee.user.util;

import com.spordee.user.dto.InitialUserSaveRequestDto;
import com.spordee.user.dto.objects.UserImagesDto;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.entity.primaryUserData.cascadetables.UserImages;
import com.spordee.user.enums.UserStatus;
import com.spordee.user.exceptions.OAuth2AuthenticationProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.spordee.user.enums.RegistrationType.FAN;
import static com.spordee.user.enums.RegistrationType.PLAYER;

@Component
public  class CommonMethods {

    private CommonMethods(){

    }

    @Value("${jwt.tokenDecryptCode}")
    private  String tokenDecryptCode;
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
                .userImage(saveUserImagesFromDto(initialUserSaveRequestDto.getUserImagesDtos(), currentTime))
                .roles(Collections.singletonList(initialUserSaveRequestDto.getRegistrationType().toString()));

        if(initialUserSaveRequestDto.getRegistrationType().equals(PLAYER)){
//            primaryUserDetails.sport(initialUserSaveRequestDto.getUserSportsDtos())
                    primaryUserDetails.gender(initialUserSaveRequestDto.getGender());


        }else if(initialUserSaveRequestDto.getRegistrationType().equals(FAN)){
            primaryUserDetails.favPlayer(initialUserSaveRequestDto.getFavPlayer())
                    .favSport(initialUserSaveRequestDto.getFavSport())
                    .secondaryFavSports(initialUserSaveRequestDto.getSecondaryFavSports())
                    .favClubTeam(initialUserSaveRequestDto.getFavClubTeam())
                    .favNationalTeam(initialUserSaveRequestDto.getFavNationalTeam())
                    .favAllTimePlayer(initialUserSaveRequestDto.getFavAllTimePlayer());
        }
        return primaryUserDetails.build();
    }


}
