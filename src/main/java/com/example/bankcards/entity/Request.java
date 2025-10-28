/*package com.example.bankcards.entity;

import com.example.bankcards.entity.enums.StatusCard;
import com.example.bankcards.entity.enums.StatusRequest;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToMany(fetch = FetchType.LAZY)
    User owner;
    @OneToMany(fetch = FetchType.LAZY)
    Card card;
    @Column(name = "status_card")
    StatusCard statusCard;
    @Column(name = "status_request")
    StatusRequest statusRequest;
    LocalDateTime created;
    LocalDateTime closed;
}*/
