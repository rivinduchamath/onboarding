package com.spordee.user.entity.sportsuserdata.cascadetables.sports;

import com.spordee.user.enums.sports.baseball.BatsBaseBall;
import com.spordee.user.enums.sports.baseball.BaseBallPosition;
import com.spordee.user.enums.sports.baseball.BaseBallThrows;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document("baseball")
public class Baseball {
    @Id
    private String id;
    private float height;
    private float weight;
    @Field("position")
    private BaseBallPosition baseBallPosition;
    @Field("bats")
    private BatsBaseBall batsBaseBall;
    @Field("throws")
    private BaseBallThrows throwHand;

}
