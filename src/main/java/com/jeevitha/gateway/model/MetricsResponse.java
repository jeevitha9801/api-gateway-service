package com.jeevitha.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetricsResponse {

    private long totalRequests;
    private long blockedRequests;
    private long averageResponseTime;
}
