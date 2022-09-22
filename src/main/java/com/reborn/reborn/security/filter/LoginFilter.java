package com.reborn.reborn.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.util.StandardCharset;
import com.reborn.reborn.dto.LoginRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String HTTP_POST = "POST";
    private static final String APPLICATION_JSON = "application/json";

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/v1/login", HTTP_POST);
    private final ObjectMapper objectMapper;

    public LoginFilter(ObjectMapper objectMapper) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.info("filter");
        if (!isPostAndJson(request)) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        LoginRequestDto emailAndPassword = getEmailAndPassword(request);

        String email = emailAndPassword.getEmail();
        String password = emailAndPassword.getPassword();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
        return super.getAuthenticationManager().authenticate(authRequest);
    }

    private LoginRequestDto getEmailAndPassword(HttpServletRequest request) {
        try {
            LoginRequestDto emailAndPassword = objectMapper
                    .readValue(StreamUtils.copyToString(
                            request.getInputStream(), StandardCharset.UTF_8), LoginRequestDto.class);;
            return emailAndPassword;
        } catch (IOException e) {
            throw new AuthenticationServiceException("아이디, 혹은 패스워드를 입력하세요");
        }
    }

    private boolean isPostAndJson(HttpServletRequest request) {
        return request.getMethod().equals(HTTP_POST) || request.getContentType().equals(APPLICATION_JSON);
    }



}
