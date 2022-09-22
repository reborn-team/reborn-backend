package com.reborn.reborn.security.filter;

import com.reborn.reborn.security.jwt.AuthToken;
import com.reborn.reborn.security.jwt.TokenProvider;
import com.reborn.reborn.security.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final MemberDetailsService memberDetailsService;
    private final static String PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //헤더에서 토큰 꺼냄
        String token = "Bearer guest";
        Optional<String> auth = Optional.ofNullable(request.getHeader(AUTHORIZATION));
        if (auth.isPresent() && !auth.get().equals("null")) {
            token = request.getHeader(AUTHORIZATION).substring(PREFIX.length());
        }

        log.info("token11={}", token);

        AuthToken authToken = tokenProvider.converterAuthToken(token);

        if (authToken.validateToken()) {
            UserDetails userDetails = memberDetailsService.loadUserByUsername(authToken.getTokenClaims().getSubject());
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            log.info("authenticationToken.getAuthorities()={}", authenticationToken.getAuthorities());

            //로그인한 Ip, SessoionId 가져와서 저장함.
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);

    }

    private String getAccessToken(HttpServletRequest request) {
        if (request.getHeader(AUTHORIZATION) != null) {
            String token = request.getHeader(AUTHORIZATION).substring(PREFIX.length());
            return token;
        }
        return null;
    }
}
