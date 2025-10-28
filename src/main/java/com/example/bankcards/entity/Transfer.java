package com.example.bankcards.entity;

import com.example.bankcards.entity.enums.StatusTransfer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
@Data
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime created;
    Integer summa;
    @Enumerated(EnumType.STRING)
    StatusTransfer status;
    @Column(name = "out_card_id")
    Long outCardId;
    @Column(name = "in_card_id")
    Long inCardId;
}
