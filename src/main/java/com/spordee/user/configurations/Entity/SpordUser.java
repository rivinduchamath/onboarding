package com.spordee.user.configurations.Entity;

import com.spordee.user.enums.AuthProvider;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString(callSuper = true)
public class SpordUser extends User {

    private String deviceId;
    private String device;
    private AuthProvider authProvider;

    public SpordUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String deviceId, String device,AuthProvider authProvider) {
        super(username, password, authorities);
        this.deviceId = deviceId;
        this.device = device;
        this.authProvider = authProvider;
    }

    @Override
    public String toString() {
        return "SpordUser{" +
                "deviceId='" + deviceId + '\'' +
                ", device='" + device + '\'' +
                '}';
    }
}
