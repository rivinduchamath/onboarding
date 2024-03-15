package com.spordee.user.entity.profiledata;

import com.querydsl.core.annotations.QueryEntity;
import com.spordee.user.annotations.CascadeSave;
import com.spordee.user.entity.primaryUserData.cascadetables.UserImages;
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
    @Field("voice_memo_link")
    private String voiceMemoLink;
    @Field("voice_memo_link_text")
    private String voiceMemoLinkText;
    @Field("user_name")
    private String userName;
    private List<String> skills;
    private Map<String, String> endorsement;
    private Map<String, String> recommendations;
    private Map<String, String> stats;
    @DBRef @CascadeSave @Field("user_mobile_numbers")
    private List<UserMobileNumber> userMobileNumbers;
    @DBRef @CascadeSave @Field("user_bookmarks")
    private List<Bookmarks> bookmarks;
    @DBRef @CascadeSave @Field("user_address")
    private List<UserAddress> userAddress;
    @DBRef @CascadeSave @Field("user_experience")
    private List<UserMobileNumber> userExperience;
    @DBRef @CascadeSave @Field("user_videos")
    private List<UserVideo> userVideos;
    @Field("created_date")
    private String createdDate; // Epoch Time
    @Field("updated_date")
    private String updatedDate;// Epoch Time



}
