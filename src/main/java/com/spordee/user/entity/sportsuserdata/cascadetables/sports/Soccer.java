package com.spordee.user.entity.sportsuserdata.cascadetables.sports;

import com.spordee.user.enums.sports.soccer.SoccerPositions;
import com.spordee.user.enums.sports.soccer.SoccerShoots;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document("soccer")
public class Soccer {
    @Id
    private String id;
    @Field("soccer_position")
    private SoccerPositions soccerPositions;
    @Field("soccer_shoots")
    private SoccerShoots soccerShoots;

}
