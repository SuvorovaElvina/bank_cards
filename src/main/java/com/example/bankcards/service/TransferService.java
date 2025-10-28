package com.example.bankcards.service;

import com.example.bankcards.dto.TransferDto;

import java.util.List;

public interface TransferService {

    TransferDto create(Long userId, Long outCardId, Long inCardId, Integer summa);

    List<TransferDto> get();
}
