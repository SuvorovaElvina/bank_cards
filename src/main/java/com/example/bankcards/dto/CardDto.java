package com.example.bankcards.dto;

import com.example.bankcards.entity.enums.StatusCard;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardDto {
    Long id;
    @NotBlank(message = "Логин не должно быть пустым.")
    @Pattern(regexp = "^\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}$")
    String number;
    LocalDate period;
    UserDto owner;
    StatusCard statusCard;
    Integer balance;
}
