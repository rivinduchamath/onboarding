package com.spordee.user.entity.profiledata.cascadetables;

import com.spordee.user.enums.AddressStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document("user_address")
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserAddress {

    @Id
    private String id;
    @Field("address_line_one")
    private String addressLineOne;
    @Field("address_line_two")
    private String addressLineTwo;
    @Field("user_name")
    private String userName;
    @Field("address_line_three")
    private String addressLineThree;
    private Point point;
    @Field("address_status")
    private AddressStatus addressStatus;
    @Field("zip_code")
    private String zipCode;
    @Field("created_date")
    private String createdDate;
    @Field("updated_date")
    private String updatedDate;

}

