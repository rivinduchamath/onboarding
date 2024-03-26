package com.spordee.user.configurations.Entity;

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
