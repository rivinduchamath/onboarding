package com.spordee.user.entity.objects;

import lombok.*;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class PlayerSportsHistory {

    private String team;
    private String startTime;
    private String endTime;

}
