package com.example.bankcards.service;

import com.example.bankcards.dto.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto create();

    List<RequestDto> getAll();

    void deleteAll(List<Long> ids);
}
