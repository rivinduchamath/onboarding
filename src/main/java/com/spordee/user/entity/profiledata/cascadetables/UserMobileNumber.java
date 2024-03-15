package com.spordee.user.entity.profiledata.cascadetables;

import com.spordee.user.enums.UserTelephoneNumberTypes;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@Document("user_mobile_number")
public class UserMobileNumber {
    @Id
    private String id;
    private String number;
    @Field("user_telephone_number_types")
    private UserTelephoneNumberTypes userTelephoneNumberTypes;
    @Field("country_code")
    private String countryCode;
    @Field("user_name")
    @Indexed
    private String userName;
    @Field("created_date")
    private String createdDate;
    @Field("updated_date")
    private String updatedDate;

}
