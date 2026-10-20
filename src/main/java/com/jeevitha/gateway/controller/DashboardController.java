package com.jeevitha.gateway.controller;

import com.jeevitha.gateway.model.DashboardResponse;
import com.jeevitha.gateway.service.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DashboardController {

    private final MetricsService metricsService;

    @GetMapping("/dashboard")
    public DashboardResponse getDashboard() {

        String status = "Healthy";

        if (metricsService.getBlockedRequests() > 0) {
            status = "Warning";
        }

        return new DashboardResponse(
                metricsService.getTotalRequests(),
                metricsService.getBlockedRequests(),
                metricsService.getAverageResponseTime(),
                3,
                status
        );
    }
}
