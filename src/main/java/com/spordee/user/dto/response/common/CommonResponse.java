package com.spordee.user.dto.response.common;

import com.spordee.user.enums.StatusType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonResponse {
    private StatusType status;
    private Object data;
    private MetaData meta;
}
