package com.spordee.user.dto.request;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PersonalInformationRequestDto {

    private String citizenship;
    private String placeOfBirth;
    private String placeOfResidence;
    private String name;
    private String userName;
    private long birthDay;
    private String userEmail;
    private String phoneNumber;
    private String homeCountry;
    private String countryCode;
    private Set<String> languages;

}
