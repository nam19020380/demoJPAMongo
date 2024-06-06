package com.example.demo.sercurity;

import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
@Data
@NoArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {

    private String id;

    private String email;

    @JsonIgnore
    private String password;

    private String username;

    private Date userBirthday;

    private String userAvatarLink;

    private String job;

    public UserDetailsImpl(String id, String email, String password, String username,
                           Date userBirthday, String userAvatarLink, String job) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.userBirthday = userBirthday;
        this.userAvatarLink = userAvatarLink;
        this.job = job;
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getUserName(),
                user.getUserBirthday(),
                user.getUserAvatarLink(),
                user.getUserJob());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}