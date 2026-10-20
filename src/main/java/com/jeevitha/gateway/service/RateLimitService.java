package com.jeevitha.gateway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class RateLimitService {

    private static final int LIMIT = 3;
    private static final long WINDOW_SIZE = 60_000; // 1 minute

    private final Map<String, RequestInfo> requestStore = new ConcurrentHashMap<>();

    public boolean isAllowed(String apiKey) {

        long currentTime = System.currentTimeMillis();

        RequestInfo requestInfo = requestStore.get(apiKey);

        if (requestInfo == null) {

            log.info("New API Key Detected: {}", apiKey);

            requestStore.put(apiKey, new RequestInfo(1, currentTime));

            log.info("API Key: {}, Count: {}", apiKey, 1);

            return true;
        }

        if (currentTime - requestInfo.windowStart > WINDOW_SIZE) {

            log.info("Window Reset for API Key: {}", apiKey);
            requestInfo.requestCount = 1;
            requestInfo.windowStart = currentTime;
            log.info("API Key: {}, Count: {}", apiKey, 1);

            return true;
        }

        if (requestInfo.requestCount >= LIMIT) {

            log.warn("Rate Limit Exceeded for API Key: {}", apiKey);

            return false;
        }

        requestInfo.requestCount++;

        log.info("API Key: {}, Count: {}", apiKey, requestInfo.requestCount);

        return true;
    }

    private static class RequestInfo {

        int requestCount;
        long windowStart;

        public RequestInfo(int requestCount, long windowStart) {
            this.requestCount = requestCount;
            this.windowStart = windowStart;
        }
    }
}