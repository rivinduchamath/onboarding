package com.spordee.user.entity.primaryUserData;


import com.spordee.user.dto.objects.UserSportsDto;
import com.spordee.user.entity.primaryUserData.cascadetables.UserImages;
import com.spordee.user.enums.Gender;
import com.spordee.user.enums.Sport;
import com.spordee.user.enums.UserStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Document(collation = "primary_user_details")
@NoArgsConstructor
@Builder
public class PrimaryUserDetails {

    @Id
    private String id;
    @Field("first_name")
    private String firstName;
    @Field("last_name")
    private String lastName;
    private Gender gender;
    private String country;
    private String city;
    @Field("is_premium")
    private boolean isPremium;
    @Field("user_name")
    private String userName;// Generated By the BackEnd
    @Field("user_status")
    private UserStatus userStatus; // Enum
    @Field("open_job")
    private boolean openJob;
    @Field("is_verified")
    private boolean isVerified;
    @Field("birth_day")
    private long birthDay;
    @Field("created_date")
    private long createdDate;
    @Field("updated_date")
    private long updatedDate;
    private List<String> roles;
//    @DBRef
    @Field("user_images")
    private List<UserImages> userImage;
    @Field("user_email")
    private String userEmail;
    private String favClubTeam;
    private String favPlayer;
    private Sport favSport;
    private Set<Sport> secondaryFavSports;
    private String favAllTimePlayer;
    private String favNationalTeam;
    private String weight;
    private String height;

}
