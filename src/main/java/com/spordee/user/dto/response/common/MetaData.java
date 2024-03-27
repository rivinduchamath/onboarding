package com.spordee.user.dto.response.common;


import com.spordee.user.enums.CommonMessages;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {
    private boolean error;
    private CommonMessages message;
    private int statusCode;
    private String description;

}
