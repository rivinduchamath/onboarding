package com.spordee.user.dto.objects.sports;

import com.spordee.user.enums.sports.baseball.BaseBallPosition;
import com.spordee.user.enums.sports.baseball.BaseBallThrows;
import com.spordee.user.enums.sports.baseball.BatsBaseBall;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class BaseballDto {
    private String id;
    private BaseBallPosition baseBallPosition;
    private BatsBaseBall batsBaseBall;
    private BaseBallThrows throwHand;

}
