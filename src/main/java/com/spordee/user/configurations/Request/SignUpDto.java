package com.spordee.user.configurations.Request;

import com.spordee.user.enums.Device;
import com.spordee.user.enums.RegistrationType;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    @NonNull
    private String username;
    @NonNull
    private RegistrationType role;

    private Device device;

    private String deviceId;

}