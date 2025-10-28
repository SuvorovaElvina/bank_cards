package com.example.bankcards.entity;

import com.example.bankcards.entity.enums.StatusCard;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Data
@Builder
@ToString
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
    @JoinColumn(name = "owner_id")
    User user;

    @Enumerated(EnumType.STRING)
    StatusCard status;

    int balance; //деньги
}
