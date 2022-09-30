package com.reborn.reborn.security.filter;

import com.reborn.reborn.security.domain.AuthToken;
import com.reborn.reborn.security.application.TokenProvider;
import com.reborn.reborn.security.application.MemberDetailsService;
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
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final MemberDetailsService memberDetailsService;
    private final static String PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = "Bearer guest";
        Optional<String> auth = Optional.ofNullable(request.getHeader(AUTHORIZATION));
        if (auth.isPresent() && !auth.get().equals("null")) {
            token = auth.get().substring(PREFIX.length());
        }

        log.info("token11={}", token);

        AuthToken authToken = tokenProvider.converterAuthToken(token);

        if (authToken.validateToken()) {
            UserDetails userDetails = memberDetailsService.loadUserByUsername(authToken.getTokenClaims().getSubject());
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            log.info("authenticationToken.getAuthorities()={}", authenticationToken.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);

    }

}
