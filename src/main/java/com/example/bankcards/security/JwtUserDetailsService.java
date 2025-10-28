package com.example.bankcards.security;

import com.example.bankcards.security.jwt.JwtUserFactory;
import com.example.bankcards.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.bankcards.entity.User user = userService.findByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with login %s not found", username));
        }

        return JwtUserFactory.create(user);
    }
}
