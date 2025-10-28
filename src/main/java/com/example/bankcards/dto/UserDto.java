package com.example.bankcards.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    @NotBlank(message = "Email не должно быть пустым.")
    @Email(message = "Введён не email.")
    String email;
    @NotBlank(message = "Логин не должно быть пустым.")
    String login;
    String name;
    @NotBlank(message = "Пароль не должно быть пустым.")
    String password;
}
