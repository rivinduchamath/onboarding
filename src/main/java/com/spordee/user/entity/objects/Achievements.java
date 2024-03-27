package com.spordee.user.entity.objects;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class Achievements {

    private String title;
    private String institute;
    private String startTime;
    private String endTime;

}
