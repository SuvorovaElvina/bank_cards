package com.example.bankcards.controller;

import com.example.bankcards.security.jwt.JwtTokenProvider;
import com.example.bankcards.dto.AuthenticationRequestDto;
import com.example.bankcards.dto.AuthenticationResponseDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @RequestMapping("/login")
    public AuthenticationResponseDto login(@RequestBody AuthenticationRequestDto auth) {
        try {
            String login = auth.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, auth.getPassword()));
            User user = userService.findByLogin(login);

            if (user == null) {
                throw new NotFoundException(String.format("User with login %s not found", login));
            }

            String token = jwtTokenProvider.createToken(login, user.getRoles());

            return new AuthenticationResponseDto(login, token);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid login or password");
        }
    }
}
