package com.example.bankcards.entity;

import com.example.bankcards.entity.enums.StatusCard;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

@Entity
@Table(name = "cards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String number; //номер карты под маской **** **** **** **** 1234

    LocalDate period; //срок действия карты

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @JoinColumn(name = "owner_id")
    User user;

    @Enumerated(EnumType.STRING)
    StatusCard status;

    int balance; //деньги
}
