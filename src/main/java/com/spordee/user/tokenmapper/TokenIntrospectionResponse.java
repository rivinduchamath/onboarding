package com.spordee.user.tokenmapper;


import com.spordee.user.tokenmapper.objects.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenIntrospectionResponse implements UserDetails {

    private Integer id;
    private String userName;
    private String email;
    private String mobile;
    private String mobileCountryCode;
    private String imageUrl;
    private boolean emailVerified;
    private boolean mobileVerified;
    private String provider;
    private String providerId;
    private List<Role> roles;

    @Override
    public String toString() {
        return "TokenIntrospectionResponse{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", mobileCountryCode='" + mobileCountryCode + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", emailVerified=" + emailVerified +
                ", mobileVerified=" + mobileVerified +
                ", provider='" + provider + '\'' +
                ", providerId='" + providerId + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

