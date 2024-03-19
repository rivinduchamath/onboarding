package com.spordee.user.entity.profiledata.cascadetables;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
@Document(collation = "bookmarks")
public class Bookmarks {
    @Id
    private String id;
    @Field("post_id")
    private String postId;
    @Field("user_name")
    private String userName;
    @Field("created_date")
    private String createdDate;
    @Field("updated_date")
    private String updatedDate;
}
