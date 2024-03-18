package com.spordee.user.entity.sportsuserdata.cascadetables.sports;

import com.spordee.user.enums.sports.hockey.HockeyPosition;
import com.spordee.user.enums.sports.hockey.HockeyShoots;
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
@Document("hockey")
public class Hockey {
    @Id
    private String id;
    @Field("hockey_position")
    private HockeyPosition hockeyPosition;
    @Field("hockey_shoots")
    private HockeyShoots hockeyShoots;
}
