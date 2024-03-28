package com.spordee.user.entity.profiledata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spordee.user.entity.objects.Achievements;
import com.spordee.user.entity.objects.InstituteDetails;
import com.spordee.user.entity.profiledata.cascadetables.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Document( "profile_data")
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
//@CompoundIndexes({
//        @CompoundIndex(name = "user_name", def = "{'email.id' : 1, 'age': 1}")
//})
public class ProfileData {
    @Id
    private String id;
    @Indexed(name = "long_description")
    private String longDescription;
    @Indexed(name = "short_description")
    private String shortDescription;
    @Indexed(name = "user_name" ,unique = true)
    private String userName;
    private String email;
    private String name;
    private String birthDay;
    @Indexed(name = "phone_number")
    private String phoneNumber;
    @Indexed(name = "birth_country")
    private String birthCountry;
    private String citizenShip;
    @Indexed(name = "place_of_birth")
    private String placeOfBirth;
    @Indexed(name = "country_of_residence")
    private String countryOfResidence;
    @Indexed(name = "place_of_residence")
    private String placeOfResidence;
    private Set<String> languages;
    private String height;
    private String weight;
    private Set<String> skills;
    private Map<String, String> endorsement;
    private Map<String, String> recommendations;
    private Map<String, String> stats;

    @Indexed(name = "user_mobile_numbers")
    private List<UserMobileNumber> userMobileNumbers;

    @Indexed(name = "user_bookmarks")
    private List<Bookmarks> bookmarks;

    @Indexed(name = "user_address")
    private List<UserAddress> userAddress;

    @Indexed(name = "user_experience")
    private List<UserExperience> userExperience;

    @Indexed(name = "user_videos")
    private List<UserVideo> userVideos;
    @Indexed(name = "created_date")
    private long createdDate; // Epoch Time
    @Indexed(name = "updated_date")
    private long updatedDate;// Epoch Time
    private List<Achievements> achievements;
    @Indexed(name = "institute_details")
    private List<InstituteDetails> instituteDetails;



}
