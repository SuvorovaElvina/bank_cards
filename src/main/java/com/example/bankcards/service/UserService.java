package com.example.bankcards.service;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.User;

import java.util.List;

public interface UserService {
    UserDto registry(UserDto userDto);

    User findByLogin(String login);

    List<UserDto> getAllById(List<Long> cardsId, Integer from, Integer size);

    UserDto updateById(Long userId, UserDto userDto);

    List<UserDto> update(List<Long> userId);

    void delete(Long userId);

    void deleteAllUserById(List<Long> usersId);

    User getUser(Long id);
}
