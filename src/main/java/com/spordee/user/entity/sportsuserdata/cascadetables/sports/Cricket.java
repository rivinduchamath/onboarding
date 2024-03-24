package com.spordee.user.entity.sportsuserdata.cascadetables.sports;

import com.spordee.user.enums.sports.cricket.BatsCricket;
import com.spordee.user.enums.sports.cricket.BowlingStyle;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document("cricket")
public class Cricket {
    @Field("bowlingStyle")
    private BowlingStyle bowlingStyle;
    private BatsCricket bats;
}
