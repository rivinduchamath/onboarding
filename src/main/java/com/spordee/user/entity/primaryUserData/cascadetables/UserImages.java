package com.spordee.user.entity.primaryUserData.cascadetables;

import com.spordee.user.enums.UserImageType;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collation = "user_images")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserImages {
    @Id
    private String id;
    @Field("image_url")
    private String imageUrl;
    @Field("is_active")
    private boolean isActive;
    @Field("image_type")
    private UserImageType imageType; // Enum
    @TextIndexed
    private String description;
    @Field("created_date")
    private String createdDate;
    @Field("updated_date")
    private String updatedDate;

}
