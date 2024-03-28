package com.spordee.user.entity.sportsuserdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spordee.user.entity.sportsuserdata.cascadetables.sports.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document( "user_sports")
public class UserSports {

    @Id
    private String id;
    @Indexed(name = "user_name",unique = true )
    private String userName;
    @Indexed(name = "soccer")
    private Soccer soccer;
    private Basketball basketball;
    private Cricket cricket;
    @Indexed(name = "american_football")
    private AmericanFootball americanFootball;
    @Indexed(name = "hockey")
    private IceHockey hockey;
    @Indexed(name = "baseball")
    private Baseball baseball;
    @Indexed(name = "rugby")
    private Rugby rugby;
    @Indexed(name = "created_date")
    private String createdDate; // Epoch Time
    @Indexed(name = "updated_date")
    private String updatedDate;// Epoch Time
}
