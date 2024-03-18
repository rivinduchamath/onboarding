package com.spordee.user.entity.profiledata;

import com.querydsl.core.annotations.QueryEntity;
import com.spordee.user.entity.profiledata.cascadetables.Bookmarks;
import com.spordee.user.entity.profiledata.cascadetables.UserAddress;
import com.spordee.user.entity.profiledata.cascadetables.UserMobileNumber;
import com.spordee.user.entity.profiledata.cascadetables.UserVideo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@QueryEntity
@Document(collation = "profile_data")
@NoArgsConstructor
public class ProfileData {
    @Id
    private String id;
    @Field("long_description")
    private String longDescription;
    @Field("short_description")
    private String shortDescription;
    @Field("user_name")
    private String userName;
    private String birthCountry;
    private String birthCity;
    private String height;
    private String weight;
    private List<String> skills;
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
    private List<UserMobileNumber> userExperience;
    @DBRef
    @Field("user_videos")
    private List<UserVideo> userVideos;
    @Field("created_date")
    private long createdDate; // Epoch Time
    @Field("updated_date")
    private long updatedDate;// Epoch Time



}
