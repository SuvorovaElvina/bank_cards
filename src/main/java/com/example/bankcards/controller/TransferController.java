package com.example.bankcards.controller;

import com.example.bankcards.dto.TransferDto;
import com.example.bankcards.service.TransferService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping("/{userId}") //user
    @ResponseStatus(HttpStatus.CREATED)
    public TransferDto createTransfer(@PathVariable @Positive Long userId,
                                      @RequestParam(name = "out") @Positive Long outCardId,
                                      @RequestParam(name = "in") @Positive Long inCardId,
                                      @RequestParam @Positive @NotNull Integer summa) {
        return transferService.create(userId, outCardId, inCardId, summa);
    }

    @GetMapping("/{userId}") //user
    public TransferDto getTransfer(@PathVariable @Positive Long userId,
                                   @RequestParam(defaultValue = "0") Integer from,
                                   @RequestParam(defaultValue = "10") Integer size) { //По id, фильтр по дате
        return null;
    }

    @GetMapping("/all/admin") //admin
    public TransferDto getTransferAll(
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) { //По id, фильтр по дате
        return null;
    }

}
