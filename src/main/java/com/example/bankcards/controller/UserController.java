package com.example.bankcards.controller;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")//user
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        return userService.registry(userDto);
    }

    @GetMapping("/admin/user") //admin
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> cardsId,
                                  @RequestParam(defaultValue = "0") Integer from,
                                  @RequestParam(defaultValue = "10") Integer size) {
        return userService.getAllById(cardsId, from, size);
    }

    @PutMapping("/user/{userId}") //user
    public UserDto updateUser(@PathVariable @Positive Long userId, @RequestBody UserDto userDto) {
        return userService.updateById(userId, userDto);
    }

    @PutMapping("/admin/user") //admin
    public List<UserDto> updateUser(@RequestParam List<Long> usersId) {
        return userService.update(usersId);
    }

    @DeleteMapping("/user/{userId}") //user
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Positive Long userId) {
        userService.delete(userId);
    }

    @DeleteMapping("/admin/user")
    @ResponseStatus(HttpStatus.NO_CONTENT) //admin
    public void deleteUserById(@RequestParam List<Long> usersId) {
        userService.deleteAllUserById(usersId);
    }
}
