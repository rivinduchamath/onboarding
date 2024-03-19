package com.spordee.user.dto.objects.sports;

import com.spordee.user.enums.sports.cricket.BatsCricket;
import com.spordee.user.enums.sports.cricket.BowlingStyle;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class CricketDto {
    private String id;
    private BowlingStyle bowlingStyle;
    private BatsCricket bats;

}
