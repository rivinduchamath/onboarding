package com.spordee.user.entity.sportsuserdata.cascadetables.sports;

import com.spordee.user.enums.sports.rugby.KickRugby;
import com.spordee.user.enums.sports.rugby.RugbyPosition;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document("rugby")
public class Rugby {
    @Id
    private String id;
    private float height;
    private float weight;
    @Field("rugby_position")
    private RugbyPosition rugbyPosition;
    @Field("kick")
    private KickRugby kickRugby;
}
