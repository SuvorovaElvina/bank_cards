package com.example.bankcards.service.impl;

import com.example.bankcards.dto.TransferDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.ActionBalance;
import com.example.bankcards.entity.enums.StatusCard;
import com.example.bankcards.exception.AccessException;
import com.example.bankcards.exception.IncorrectException;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.TransferService;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final UserService userService;
    private final CardRepository cardRepository;
    private final CardService cardService;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TransferDto create(Long userId, Long outCardId, Long inCardId, Integer summa) {
        User user = userService.getUser(userId);
        Card outCard = cardRepository.findById(outCardId)
                .orElseThrow(() -> new NotFoundException(String.format("Не найдена карта с id %d", outCardId)));
        Card inCard = cardRepository.findById(inCardId)
                .orElseThrow(() -> new NotFoundException(String.format("Не найдена карта с id %d", inCardId)));

        validateForTransfer(user, outCard, inCard, summa);

        cardService.updateBalance(userId, outCardId, summa, ActionBalance.MINUS.toString());
        cardService.updateBalance(userId, inCardId, summa, ActionBalance.PLUS.toString());

        return null;
    }

    @Override
    public List<TransferDto> get() {
        return null;
    }

    private void validateForTransfer(User user, Card outCard, Card inCard, Integer summa) {
        if (!user.getId().equals(outCard.getUser().getId()) || !user.getId().equals(inCard.getUser().getId())) {
            throw new AccessException("Вы не являетесь владельцем этих карт, в переводе - отказано.");
        } else if (outCard.getStatus() != StatusCard.ACTIVE || inCard.getStatus() != StatusCard.ACTIVE) {
            throw new IncorrectException("Проверьте чтоб карты, которые вы хотите использовать, были активны.");
        } else if (outCard.getBalance() < summa) {
            throw new IncorrectException("На карте недостаточно денег для списания");
        }
    }
}
