package com.example.bankcards.security.jwt;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(@Nullable String message, Throwable cause) {
        super(message, cause);
    }

    public JwtAuthenticationException(@Nullable String message) {
        super(message);
    }
}
