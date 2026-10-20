package com.jeevitha.gateway.filter;

import com.jeevitha.gateway.service.MetricsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(2)
public class LoggingFilter extends OncePerRequestFilter {
    private final MetricsService metricsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();
        String path = request.getRequestURI();

        if (path.startsWith("/api")) {
            metricsService.incrementTotalRequests();
        }
        String apiKey = request.getHeader("X-API-KEY");

        log.info(
                "API_KEY={} METHOD={} ENDPOINT={} TIME={}",
                apiKey,
                request.getMethod(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        filterChain.doFilter(request, response);
        long responseTime =
                System.currentTimeMillis() - startTime;
        if (path.startsWith("/api")) {
            metricsService.addResponseTime(responseTime);
            log.info("Response Time = {} ms", responseTime);
        }
    }
}
