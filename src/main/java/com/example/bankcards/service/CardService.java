package com.example.bankcards.service;

import com.example.bankcards.dto.CardDto;

import java.util.List;

public interface CardService {
    CardDto create(Long userId, CardDto cardDto);

    List<CardDto> getAllByUser(Long userId, Integer from, Integer size);

    List<CardDto> getCards(List<Long> cardIds, Integer from, Integer size);

    Integer getBalance(Long userId, Long cardId);

    Integer updateBalance(Long userId, Long cardIs, Integer summa, String action);

    List<CardDto> update(List<Long> cardIds, String status);

    void deleteAllById(List<Long> cardsId);

}
