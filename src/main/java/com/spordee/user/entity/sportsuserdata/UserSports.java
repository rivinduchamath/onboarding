package com.spordee.user.entity.sportsuserdata;

import com.spordee.user.entity.sportsuserdata.cascadetables.sports.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
@Document("user_sports")
public class UserSports {

    @Id
    @Field("user_name")
    private String userName;
    @Field("soccer")
    private Soccer soccer;
    private Basketball basketball;
    private Cricket cricket;
    @Field("american_football")
    private AmericanFootball americanFootball;
    @Field("hockey")
    private IceHockey hockey;
    @Field("baseball")
    private Baseball baseball;
    @Field("rugby")
    private Rugby rugby;
    @Field("created_date")
    private String createdDate; // Epoch Time
    @Field("updated_date")
    private String updatedDate;// Epoch Time
}
