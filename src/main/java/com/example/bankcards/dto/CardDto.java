package com.example.bankcards.dto;

import com.example.bankcards.entity.enums.StatusCard;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardDto {
    Long id;
    @NotBlank(message = "Логин не должно быть пустым.")
    @Pattern(regexp = "^\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}$")
    String number; //номер карты под маской **** **** **** **** 1234
    LocalDate period; //срок действия карты
    UserDto owner;
    StatusCard statusCard;
    Integer balance;
}
