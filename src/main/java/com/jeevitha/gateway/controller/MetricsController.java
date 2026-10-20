package com.jeevitha.gateway.controller;

import com.jeevitha.gateway.model.MetricsResponse;
import com.jeevitha.gateway.service.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MetricsController {

    private final MetricsService metricsService;

    @GetMapping("/metrics")
    public MetricsResponse getMetrics(){
        return new MetricsResponse(metricsService.getTotalRequests(), metricsService.getBlockedRequests(), metricsService.getAverageResponseTime());
    }
}
