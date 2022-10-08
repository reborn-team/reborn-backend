package com.reborn.reborn.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reborn.reborn.common.exception.dto.ErrorResponse;
import com.reborn.reborn.security.exception.ExpiredTokenException;
import com.reborn.reborn.security.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenExceptionFilter extends OncePerRequestFilter {
    private static final String UTF_8 = "UTF-8";
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredTokenException | InvalidTokenException e) {
            response.setStatus(e.getHttpStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(UTF_8);

            ErrorResponse error = new ErrorResponse(e.getMessage());
            objectMapper.writeValue(response.getWriter(), error);
        }
    }
}
