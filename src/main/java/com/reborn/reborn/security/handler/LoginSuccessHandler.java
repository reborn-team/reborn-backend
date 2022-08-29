package com.reborn.reborn.security.handler;


import com.reborn.reborn.security.UserPrincipal;
import com.reborn.reborn.security.jwt.AuthToken;
import com.reborn.reborn.security.jwt.JwtConfig;
import com.reborn.reborn.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private static final String TOKEN_PREFIX = "Bearer ";
    //30분
    private long expireDate = 1800000;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                principal.getMember().getEmail(),
                principal.getMember().getMemberRole(),
                new Date(now.getTime() + expireDate)
        );
        response.setHeader(AUTHORIZATION, TOKEN_PREFIX+accessToken.getToken());

    }
}
