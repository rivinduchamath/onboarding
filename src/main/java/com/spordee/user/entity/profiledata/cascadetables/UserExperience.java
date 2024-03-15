package com.spordee.user.entity.profiledata.cascadetables;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collation = "user_experience")
public class UserExperience {

    @Id
    private String id;
    private String position;
    @Field("user_name")
    @Indexed
    private String userName;
    @Field("joined_date")
    private String joinedDate;
    @Field("left_date")
    private String leftDate;
    @Field("created_date")
    private String createdDate;
    @Field("updated_date")
    private String updatedDate;
}
