package com.jeevitha.gateway.filter;

import com.jeevitha.gateway.service.ApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Order(1)
public class ApiKeyFilter extends OncePerRequestFilter {

    private final ApiKeyService apiKeyService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.equals("/")
                || path.equals("/index.html")
                || path.equals("/style.css")
                || path.equals("/script.js")
                || path.equals("/metrics")
                || path.equals("/dashboard")) {

            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader("X-API-KEY");

        if (apiKey == null || !apiKeyService.isValid(apiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid API Key");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
