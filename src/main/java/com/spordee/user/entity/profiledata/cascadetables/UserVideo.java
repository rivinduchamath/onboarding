package com.spordee.user.entity.profiledata.cascadetables;

import com.spordee.user.enums.UserVideoType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
@Document(collation = "user_video")
public class UserVideo {

    @Id
    private String id;
    @Field("is_active")
    private boolean isActive;
    @Field("user_video_type")
    private UserVideoType userVideoType;
    @Field("user_name") @Indexed
    private String userName;
    @TextIndexed
    private String description;
    @Field("created_date")
    private String createdDate;
    @Field("updated_date")
    private String updatedDate;

}
