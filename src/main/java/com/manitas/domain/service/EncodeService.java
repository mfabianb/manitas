package com.manitas.domain.service;

import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface EncodeService {
    @Scheduled(cron = "${crud.generate.cron}")
    void generateKeys() throws NoSuchAlgorithmException, IOException;

    String encode(String message);

    String decode(String message);
}
