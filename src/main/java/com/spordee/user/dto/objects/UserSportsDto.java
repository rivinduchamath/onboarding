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

    private Soccer soccer = new Soccer();
    private Basketball basketball = new Basketball();
    private Cricket cricket = new Cricket();
    private AmericanFootball americanFootball = new AmericanFootball();
    private IceHockey iceHockey = new IceHockey();
    private Baseball baseball = new Baseball();
    private Rugby rugby = new Rugby();
    private String createdDate; // Epoch Time
    private String updatedDate;// Epoch Time

}
