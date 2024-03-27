package com.spordee.user.dto.request;

import com.spordee.user.enums.AuthProvider;
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
    private AuthProvider authProvider;
    private String providerId;

    private RegistrationType role;
    @NonNull
    private Device device;
    @NonNull
    private String deviceId;

}