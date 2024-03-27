package com.spordee.user.entity.objects;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class InstituteDetails {

    private String institute;
    private String typeOfInstitute;
    private String startTime;
    private String endTime;

}
