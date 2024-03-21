package com.spordee.user.dto.objects;

import com.spordee.user.entity.sportsuserdata.cascadetables.sports.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class UserSportsDto {

    private Soccer soccer;
    private Basketball basketball;
    private Cricket cricket;
    private AmericanFootball americanFootball;
    private Hockey hockey;
    private Baseball baseball;
    private Rugby rugby;
    private String createdDate; // Epoch Time
    private String updatedDate;// Epoch Time

}
