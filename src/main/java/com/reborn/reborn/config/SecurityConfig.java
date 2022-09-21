package com.reborn.reborn.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reborn.reborn.security.filter.LoginFilter;
import com.reborn.reborn.security.filter.LoginSuccessHandler;
import com.reborn.reborn.security.filter.TokenAuthenticationFilter;
import com.reborn.reborn.security.jwt.TokenProvider;
import com.reborn.reborn.security.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {

    private final MemberDetailsService memberDetailsService;
    private final ObjectMapper objectMapper;
    private final TokenProvider tokenProvider;
    private final LoginSuccessHandler loginSuccessHandler;
    private final AuthenticationConfiguration configuration;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .formLogin().disable();
        http.addFilterBefore(localMemberLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }


    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public LoginFilter localMemberLoginFilter() throws Exception {
        LoginFilter filter = new LoginFilter(objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
        return filter;
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(){
        return new TokenAuthenticationFilter(tokenProvider,memberDetailsService);
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //    @Bean
//    public AuthenticationManager authManager(HttpSecurity http, UserDetailsService userDetailsService)
//            throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder())
//                .and()
//                .build();
//    }

    //    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .formLogin().disable();
//        http.userDetailsService(memberDetailsService);
//        http.addFilterBefore(localMemberLoginFilter(),
//                UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
}
