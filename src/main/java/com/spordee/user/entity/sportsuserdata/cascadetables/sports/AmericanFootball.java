package com.spordee.user.entity.sportsuserdata.cascadetables.sports;

import com.spordee.user.enums.sports.americanfootball.PositionsAmericanFootBall;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document("american_football")
public class AmericanFootball {
    @Id
    private String id;
    private float height;
    private float weight;
    private String arm;
    private String hand;
    @Field("positions")
    private PositionsAmericanFootBall positionsAmericanFootBall;
}
