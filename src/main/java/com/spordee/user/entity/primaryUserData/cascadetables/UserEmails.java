package com.spordee.user.entity.primaryUserData.cascadetables;

import com.spordee.user.enums.UserEmail;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collation = "user_emails")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserEmails {

    @Id
    private String id;
    @Field("email_status")
    private UserEmail emailStatus; // Enum
    @Field("created_date")
    private String createdDate;
    @Field("user_name")
    @Indexed
    private String userName;
    @Field("updated_date")
    private String updatedDate;


}
