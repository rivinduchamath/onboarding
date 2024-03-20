package com.spordee.user.response.common;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {
    private boolean error;
    private int statusCode;
    private String description;

//    public MetaData(boolean error, CommonMessages message, int statusCode) {
//        this.error = error;
//        this.message = message;
//        this.statusCode = statusCode;
//    }
}
