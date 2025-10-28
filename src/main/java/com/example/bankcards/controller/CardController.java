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
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED) //admin
    public CardDto createCard(@RequestParam @Positive Long userId, @Valid @RequestBody CardDto cardDto) {
        return cardService.create(userId, cardDto);
    }

    @GetMapping("/{userId}/my_cards") //user
    public List<CardDto> getCardsByUserId(@PathVariable @Positive Long userId,
                                      @RequestParam(defaultValue = "0") Integer from,
                                      @RequestParam(defaultValue = "10") Integer size) {
        return cardService.getAllByUser(userId, from, size);
    }

    @GetMapping("/admin/all") //admin
    public List<CardDto> getCards(@RequestParam(required = false) List<Long> cardsId,
                                  @RequestParam(defaultValue = "0") Integer from,
                                  @RequestParam(defaultValue = "10") Integer size) {
        return cardService.getCards(cardsId, from, size);
    }

    @GetMapping("/{userId}/balance/{cardId}") //user
    public Integer getBalance(@PathVariable @Positive Long userId, @PathVariable Long cardId) {
        return cardService.getBalance(userId, cardId);
    }

    @PutMapping("/{userId}/balance/{cardId}") //user
    public Integer updateBalance(@PathVariable @Positive Long userId,
                              @PathVariable Long cardId,
                              @RequestParam @Positive @NotNull Integer summa,
                              @RequestParam String action) {
        return cardService.updateBalance(userId, cardId, summa, action);
    }

    @PutMapping("/admin") //admin
    public List<CardDto> updateCards(@RequestParam List<Long> cardsId,
                                     @RequestParam String status) {
        return cardService.update(cardsId, status);
    }

    @DeleteMapping("/admin")
    @ResponseStatus(HttpStatus.NO_CONTENT) //admin
    public void deleteCard(@RequestParam List<Long> cardsId) {
        cardService.deleteAllById(cardsId);
    }

    /*

    @GetMapping("/search")
    public List<CardDto> getCards(@RequestHeader("X-Sharer-User-Id") @Positive Long userId,
                                  @RequestParam(defaultValue = "ACTIVE") String status,
                                  @RequestParam(required = false) String number,
                                  @RequestParam(defaultValue = "0") Integer from,
                                  @RequestParam(defaultValue = "10") Integer size) {
        return cardServiceImpl.getAll(userId, status, number, from, size);
    }

    */
}
