package com.spordee.user.dto.objects.sports;

import com.spordee.user.enums.sports.basketball.BasketballPosition;
import com.spordee.user.enums.sports.basketball.BasketballShoots;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class BasketballDto {

    private String id;

    private float wingspan;
    private BasketballPosition basketballPosition;
    private BasketballShoots basketballShoots;
}
