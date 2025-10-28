package com.example.bankcards.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {
    private final Long id;
    private final String email;
    private final String login;
    private final String name;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(Long id,
                   String email,
                   String login,
                   String name,
                   String password,
                   Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
