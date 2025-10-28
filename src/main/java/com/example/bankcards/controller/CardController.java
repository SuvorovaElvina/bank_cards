package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.service.CardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/admin/card")
    @ResponseStatus(HttpStatus.CREATED) //admin
    public CardDto createCard(@RequestParam @Positive Long userId, @Valid @RequestBody CardDto cardDto) {
        return cardService.create(userId, cardDto);
    }

    @GetMapping("/card/{userId}/my_cards") //user
    public List<CardDto> getCardsByUserId(@PathVariable @Positive Long userId,
                                          @RequestParam(defaultValue = "0") Integer from,
                                          @RequestParam(defaultValue = "10") Integer size) {
        return cardService.getAllByUser(userId, from, size);
    }

    @GetMapping("/admin/card/all") //admin
    public List<CardDto> getCards(@RequestParam(required = false) List<Long> cardsId,
                                  @RequestParam(defaultValue = "0") Integer from,
                                  @RequestParam(defaultValue = "10") Integer size) {
        return cardService.getCards(cardsId, from, size);
    }

    @GetMapping("/card/{userId}/balance/{cardId}") //user
    public Integer getBalance(@PathVariable @Positive Long userId, @PathVariable Long cardId) {
        return cardService.getBalance(userId, cardId);
    }

    @PutMapping("/card/{userId}/balance/{cardId}") //user
    public Integer updateBalance(@PathVariable @Positive Long userId,
                                 @PathVariable Long cardId,
                                 @RequestParam @Positive @NotNull Integer summa,
                                 @RequestParam String action) {
        return cardService.updateBalance(userId, cardId, summa, action);
    }

    @PutMapping("/admin/card") //admin
    public List<CardDto> updateCards(@RequestParam List<Long> cardsId,
                                     @RequestParam String status) {
        return cardService.update(cardsId, status);
    }

    @DeleteMapping("/admin/card")
    @ResponseStatus(HttpStatus.NO_CONTENT) //admin
    public void deleteCard(@RequestParam List<Long> cardsId) {
        cardService.deleteAllById(cardsId);
    }
}
