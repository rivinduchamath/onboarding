package com.spordee.user.dto.objects.sports;

import com.spordee.user.enums.sports.hockey.HockeyPosition;
import com.spordee.user.enums.sports.hockey.HockeyShoots;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class HockeyDto {
    private String id;
    private HockeyPosition hockeyPosition;
    private HockeyShoots hockeyShoots;
}
