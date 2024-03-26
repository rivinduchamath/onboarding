package com.spordee.user.entity.profiledata;

import com.spordee.user.entity.profiledata.cascadetables.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Document(collation = "profile_data")
@NoArgsConstructor
@Builder
public class ProfileData {
    @Id
    private String id;
    @Field("long_description")
    private String longDescription;
    @Field("short_description")
    private String shortDescription;
    @Field("user_name")
    private String userName;
    private String email;
    private String name;
    private String birthDay;
    @Field("phone_number")
    private String phoneNumber;
    @Field("birth_country")
    private String birthCountry;
    private String citizenShip;
    private String birthCity;
    @Field("country_of_residence")
    private String countryOfResidence;
    @Field("city_of_residence")
    private String cityOfResidence;
    private Set<String> languages;
    private String height;
    private String weight;
    private Set<String> skills;
    private Map<String, String> endorsement;
    private Map<String, String> recommendations;
    private Map<String, String> stats;
    @DBRef
    @Field("user_mobile_numbers")
    private List<UserMobileNumber> userMobileNumbers;
    @DBRef
    @Field("user_bookmarks")
    private List<Bookmarks> bookmarks;
    @DBRef
    @Field("user_address")
    private List<UserAddress> userAddress;
    @DBRef
    @Field("user_experience")
    private List<UserExperience> userExperience;
    @DBRef
    @Field("user_videos")
    private List<UserVideo> userVideos;
    @Field("created_date")
    private long createdDate; // Epoch Time
    @Field("updated_date")
    private long updatedDate;// Epoch Time



}
