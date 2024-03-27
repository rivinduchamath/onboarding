package com.spordee.user.dto.request;

import com.spordee.user.dto.objects.UserSportsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class SpecsInfoRequestDto {
    private UserSportsDto userSportsDto;
    private String weight;
    private String height;
    private String userName;
}
