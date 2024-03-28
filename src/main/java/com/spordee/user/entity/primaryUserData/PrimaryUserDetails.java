package com.spordee.user.entity.primaryUserData;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.spordee.user.entity.primaryUserData.cascadetables.UserImages;
import com.spordee.user.entity.sportsuserdata.UserSports;
import com.spordee.user.enums.Sport;
import com.spordee.user.enums.SportsGender;
import com.spordee.user.enums.UserStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@ToString
@Document(  "primary_user_details")
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrimaryUserDetails {

    @Id
    private String id;
    @Indexed(name = "name")
    private String name;
    @Indexed(name = "birth_day")
    private long birthDay;
    @Indexed(name = "user_email")
    private String userEmail;
    @Indexed(name = "phone_number")
    private String phoneNumber;
    @Indexed(name = "home_country")
    private String homeCountry;
    @Indexed(name = "sports_gender")
    private SportsGender sportsGender;
    @Indexed(name = "country_code")
    private String countryCode;
    private Set<String> languages;
    private String city;
    @Indexed(name = "is_premium")
    private boolean isPremium;
    @Indexed(name = "user_name", unique = true)
    private String userName;// Generated By the BackEnd
    @Indexed(name = "user_status")
    private UserStatus userStatus; // Enum
    @Indexed(name = "open_job")
    private boolean openJob;
    @Indexed(name = "is_verified")
    private boolean isVerified;

    @Indexed(name = "created_date")
    private long createdDate;
    @Indexed(name = "updated_date")
    private long updatedDate;
    private List<String> roles;
//    @DBRef
    @Indexed(name = "user_images")
    private List<UserImages> userImage;
    
    private UserSports userSports;
    private String favClubTeam;
    private String favPlayer;
    private Sport favSport;
    private Set<Sport> secondaryFavSports;
    private String favAllTimePlayer;
    private String favNationalTeam;



}
