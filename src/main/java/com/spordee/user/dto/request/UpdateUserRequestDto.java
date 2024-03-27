package com.spordee.user.dto.request;

import com.spordee.user.entity.objects.Achievements;
import com.spordee.user.entity.objects.InstituteDetails;
import com.spordee.user.entity.objects.PlayerSportsHistory;
import com.spordee.user.dto.objects.UserSportsDto;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateUserRequestDto {

    //personal Details
    private String name;
    private long dateOfBirth;
    private String email;
    private String phoneNumber;
    private String citizenship;
    private String homeCountry;
    private String countryOfBirth;
    private String cityOfBirth;
    private String countryOfResidence;
    private String cityOfResidence;
    private Set<String> languages;

    private UserSportsDto userSportsDto;
    private String weight;
    private String height;

    private Set<String> skills;

    // for player
    private List<PlayerSportsHistory> sportsHistory;
    private List<InstituteDetails> instituteDetails;
    private List<Achievements> achievements;

}
