package com.jeevitha.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardResponse {

    private long totalRequests;
    private long blockedRequests;
    private long averageResponseTime;
    private int rateLimitPerMinute;
    private String status;
}
