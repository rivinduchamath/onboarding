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
@NoArgsConstructor
@Document("user_sports")
public class UserSports {

    @Id
    @Field("user_name")
    private String userName;
    @DBRef(lazy = true)
    @Field("soccer")
    private Soccer soccer;
    @DBRef
    private Basketball basketball;
    @DBRef
    private Cricket cricket;
    @DBRef
    @Field("american_football")
    private AmericanFootball americanFootball;
    @DBRef
    @Field("hockey")
    private IceHockey hockey;
    @DBRef
    @Field("baseball")
    private Baseball baseball;
    @DBRef
    @Field("rugby")
    private Rugby rugby;

    @Field("created_date")
    private String createdDate; // Epoch Time
    @Field("updated_date")
    private String updatedDate;// Epoch Time
}
