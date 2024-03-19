package com.spordee.user.dto.objects.sports;

import com.spordee.user.enums.sports.soccer.SoccerPositions;
import com.spordee.user.enums.sports.soccer.SoccerShoots;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class SoccerDto {
    private String id;
    private SoccerPositions soccerPositions;
    private SoccerShoots soccerShoots;

}
