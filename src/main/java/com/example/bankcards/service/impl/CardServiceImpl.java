package com.example.bankcards.service.impl;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.ActionBalance;
import com.example.bankcards.entity.enums.StatusCard;
import com.example.bankcards.exception.IncorrectException;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.exception.ValidationException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.mapper.CardMapper;
import com.example.bankcards.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository repository;
    private final UserService userService;
    private final CardMapper cardMapper;
    private final UserMapper userMapper;

    @Override
    public CardDto create(Long userId, CardDto cardDto) {
        Card card = cardMapper.toCard(cardDto);
        card.setUser(userService.getUser(userId));
        card.setStatus(StatusCard.ACTIVE);
        card.setPeriod(LocalDate.now().plusYears(3)); //убрать жёсткую надстройку
        Card cardNew = repository.save(card);
        return cardMapper.toCardDto(cardNew, userMapper.toUserDto(cardNew.getUser()));
    }

    @Override
    public List<CardDto> getAllByUser(Long userId, Integer from, Integer size) {
        User user = userService.getUser(userId);
        int pageNumber = (int) Math.ceil((double) from / size);
        Page<Card> cards = repository.findByUserId(userId, PageRequest.of(pageNumber, size));
        return cards.stream().map(card -> cardMapper.toCardDto(card, userMapper.toUserDto(user))).collect(Collectors.toList());
    }

    @Override
    public List<CardDto> getCards(List<Long> cardIds, Integer from, Integer size) {
        int pageNumber = (int) Math.ceil((double) from / size);
        Page<Card> cards;
        if (cardIds == null) {
            cards = repository.findAll(PageRequest.of(pageNumber, size));
        } else {
            cards = repository.findAllByIdIn(cardIds, PageRequest.of(pageNumber, size));
        }
        return cards.stream().map(card -> cardMapper.toCardDto(card, userMapper.toUserDto(card.getUser()))).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getBalance(Long userId, Long cardId) {
        Optional<Card> optional = repository.findById(cardId);

        Card card = optional.orElseThrow(() -> new NotFoundException(String.format("Карты с id %d - не существует.", cardId)));
        if (!card.getUser().getId().equals(userId)) {
            throw new ValidationException(String.format("Пользователь с id %d - не является владельцем карты.", userId));
        }

        return card.getBalance();
    }

    @Override
    @Transactional
    public Integer updateBalance(Long userId, Long cardId, Integer summa, String action) {
        Optional<Card> optional = repository.findById(cardId);

        Card card = optional.orElseThrow(() -> new NotFoundException(String.format("Карты с id %d - не существует.", cardId)));
        if (!card.getUser().getId().equals(userId)) {
            throw new ValidationException(String.format("Пользователь с id %d - не является владельцем карты.", userId));
        }

        try {
            if (ActionBalance.valueOf(action.toUpperCase()).equals(ActionBalance.PLUS)) {
                card.setBalance(card.getBalance() + summa);
            } else {
                if (card.getBalance() - summa < 0) {
                    throw new IncorrectException("Не корректная сумма невозможно снять");
                }

                card.setBalance(card.getBalance() - summa);
            }
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Статус не соответстует одному из " + Arrays.toString(ActionBalance.values()));
        }

        repository.save(card);
        return card.getBalance();
    }

    @Override
    public List<CardDto> update(List<Long> cardsId, String status) {
        try {
            List<Card> cards = repository.findAllById(cardsId);
            List<Card> update;
            if (StatusCard.valueOf(status.toUpperCase()).equals(StatusCard.ACTIVE)) {
                List<Card> expired = cards.stream().filter(card -> card.getStatus().equals(StatusCard.EXPIRED)).toList();
                List<Card> updateDate = new ArrayList<>(expired.stream().peek(card -> card.setPeriod(LocalDate.now().plusYears(3))).toList()); // убрать жёсткость
                List<Card> notExpired = cards.stream().filter(card -> !card.getStatus().equals(StatusCard.EXPIRED)).toList();
                updateDate.addAll(notExpired);
                update = updateDate.stream().peek(card -> card.setStatus(StatusCard.valueOf(status.toUpperCase()))).toList();
                return repository.saveAll(update).stream()
                        .map(card -> cardMapper.toCardDto(card, userMapper.toUserDto(card.getUser()))).toList();
            } else {
                cards.forEach(i -> i.setStatus(StatusCard.valueOf(status.toUpperCase())));
                return repository.saveAll(cards).stream()
                        .map(card -> cardMapper.toCardDto(card, userMapper.toUserDto(card.getUser()))).toList();
            }
        } catch (RuntimeException e) {
            throw new ValidationException("Статус не соответстует одному из " + Arrays.toString(StatusCard.values()));
        }

    }

    @Override
    public void deleteAllById(List<Long> cardsId) {
        repository.deleteAllById(cardsId);
    }
}
