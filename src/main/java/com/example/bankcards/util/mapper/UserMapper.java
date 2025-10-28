package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .login(userDto.getLogin())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .login(user.getLogin())
                .email(user.getEmail())
                .build();
    }
}
