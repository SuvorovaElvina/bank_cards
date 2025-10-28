package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.util.Mask;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {
    public Card toCard(CardDto cardDto) {
        return Card.builder()
                .number(cardDto.getNumber()).build();
    }

    public CardDto toCardDto(Card card, UserDto userDto) {
        return CardDto.builder()
                .id(card.getId())
                .statusCard(card.getStatus())
                .balance(card.getBalance())
                .period(card.getPeriod())
                .number(Mask.maskNumberCard(card.getNumber()))
                .owner(userDto)
                .build();
    }
}
