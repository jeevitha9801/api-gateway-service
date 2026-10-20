package com.jeevitha.gateway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class MetricsService {

    private final AtomicLong totalRequests = new AtomicLong();
    private final AtomicLong blockedRequests = new AtomicLong();

    private final AtomicLong totalResponseTime = new AtomicLong();
    private final AtomicLong successfulRequests = new AtomicLong();

    public void incrementTotalRequests() {
        long count = totalRequests.incrementAndGet();
        log.info("Total Requests = {}", count);
    }

    public void incrementBlockedRequests() {
        long count = blockedRequests.incrementAndGet();
        log.info("Blocked Requests = {}", count);
    }

    public long getTotalRequests() {
        return totalRequests.get();
    }

    public long getBlockedRequests() {
        return blockedRequests.get();
    }

    public void addResponseTime(long responseTime) {

        totalResponseTime.addAndGet(responseTime);
        successfulRequests.incrementAndGet();
    }

    public long getAverageResponseTime() {

        if (successfulRequests.get() == 0) {
            return 0;
        }

        return totalResponseTime.get() /
                successfulRequests.get();
    }
}