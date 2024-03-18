package com.spordee.user.entity.sportsuserdata;

import com.spordee.user.annotations.CascadeSave;
import com.spordee.user.entity.profiledata.cascadetables.UserVideo;
import com.spordee.user.entity.sportsuserdata.cascadetables.sports.Baseball;
import com.spordee.user.entity.sportsuserdata.cascadetables.sports.Basketball;
import com.spordee.user.entity.sportsuserdata.cascadetables.sports.Hockey;
import com.spordee.user.entity.sportsuserdata.cascadetables.sports.Soccer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Document("user_sports")
public class UserSports {

    @Id
    @Field("user_name")
    private String userName;
    @DBRef
    @CascadeSave
    @Field("soccer")
    private Soccer soccer;
    @DBRef
    @CascadeSave
    private List<Basketball> basketball;

    @DBRef
    @CascadeSave
    @Field("hockey")
    private List<Hockey> hockey;
    @DBRef
    @CascadeSave
    @Field("baseball")
    private List<Baseball> baseball;

    @Field("created_date")
    private String createdDate; // Epoch Time
    @Field("updated_date")
    private String updatedDate;// Epoch Time
}
