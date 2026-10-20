package com.jeevitha.gateway.filter;

import com.jeevitha.gateway.service.MetricsService;
import com.jeevitha.gateway.service.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Order(3)
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;
    private final MetricsService metricsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.equals("/") || path.equals("/index.html") || path.equals("/style.css")
                || path.equals("/script.js") || path.equals("/metrics") || path.equals("/dashboard")) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader("X-API-KEY");

        if (!rateLimitService.isAllowed(apiKey)) {

            metricsService.incrementBlockedRequests();

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());

            response.getWriter().write(
                    "Rate limit exceeded. Try again later."
            );

            return;
        }
        filterChain.doFilter(request, response);
    }
}
