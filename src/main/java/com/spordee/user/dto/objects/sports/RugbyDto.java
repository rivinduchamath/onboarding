package com.spordee.user.dto.objects.sports;

import com.spordee.user.enums.sports.rugby.KickRugby;
import com.spordee.user.enums.sports.rugby.RugbyPosition;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class RugbyDto {
    private String id;
    private RugbyPosition rugbyPosition;

    private KickRugby kickRugby;
}
