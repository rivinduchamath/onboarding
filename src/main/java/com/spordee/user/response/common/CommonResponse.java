package com.spordee.user.response.common;

import com.spordee.user.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
    private StatusType status;
    private Object data;
    private MetaData meta;
}
