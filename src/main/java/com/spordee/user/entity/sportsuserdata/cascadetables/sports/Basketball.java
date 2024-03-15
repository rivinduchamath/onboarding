package com.spordee.user.entity.sportsuserdata.cascadetables.sports;

import com.spordee.user.enums.sports.basketball.BasketballPosition;
import com.spordee.user.enums.sports.basketball.BasketballShoots;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document("basketball")
public class Basketball {
    @Id
    private String id;
    private float height;
    private float weight;
    private float wingspan;
    @Field("basket_ball_position")
    private BasketballPosition basketballPosition;
    @Field("basket_ball_shoots")
    private BasketballShoots basketballShoots;
}
