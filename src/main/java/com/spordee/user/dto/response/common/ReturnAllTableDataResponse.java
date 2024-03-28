package com.spordee.user.dto.response.common;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnAllTableDataResponse<T> {
    private T profileData;
    private T primaryData;
    private T sportsData;
}
