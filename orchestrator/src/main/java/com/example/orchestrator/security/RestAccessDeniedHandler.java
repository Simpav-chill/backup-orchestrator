package com.example.orchestrator.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exception
    ) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/problem+json");

        String json = """
                {
                  "type": "about:blank",
                  "title": "Forbidden",
                  "status": 403,
                  "detail": "You don't have permission to access this source",
                  "instance": "%s"
                }
                """.formatted(request.getRequestURI());

        response.getWriter().write(json);
    }
}
