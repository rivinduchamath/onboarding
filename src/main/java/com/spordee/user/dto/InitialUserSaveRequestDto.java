package com.spordee.user.dto;

import com.spordee.user.dto.objects.UserImagesDto;
import com.spordee.user.dto.objects.UserSportsDto;
import com.spordee.user.enums.Gender;
import com.spordee.user.enums.RegistrationType;
import com.spordee.user.enums.Sport;
import com.spordee.user.enums.UserStatus;
import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class InitialUserSaveRequestDto {
    private String id;
    private String firstName;
    private RegistrationType registrationType;
    private String lastName;
    private Gender gender;
    private String country;
    private String city;
    private boolean isPremium;// Generated By the BackEnd
    private UserStatus userStatus; // Enum
    private boolean openJob;
    private boolean isVerified;
    private long birthDay;
    private long createdDate;
    private long updatedDate;
    private String weight;
    private String height;
    private List<UserImagesDto> userImagesDtos;
    private UserSportsDto userSportsDtos;
    private String favPlayer;
    private String favClubTeam;
    private String favAllTimePlayer;
    private String favNationalTeam;
    private Sport favSport;
    private Set<Sport> secondaryFavSports;

    private String userEmail;

}
