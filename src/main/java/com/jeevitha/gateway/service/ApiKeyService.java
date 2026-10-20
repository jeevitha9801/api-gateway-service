package com.jeevitha.gateway.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ApiKeyService {

    private final Set<String> validApiKeys;

    public ApiKeyService(@Value("${gateway.api.keys}")
                         String apiKeys){
        this.validApiKeys = Arrays.stream(apiKeys.split(",")).map(String::trim).collect(Collectors.toSet());
    }

    public boolean isValid(String apiKey) {
        return validApiKeys.contains(apiKey);
    }
}
