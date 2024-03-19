package com.spordee.user.dto.objects.sports;

import com.spordee.user.enums.sports.americanfootball.PositionsAmericanFootBall;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class AmericanFootBallDto {

    private String id;
    private String arm;
    private String hand;
    private PositionsAmericanFootBall positionsAmericanFootBall;
}
