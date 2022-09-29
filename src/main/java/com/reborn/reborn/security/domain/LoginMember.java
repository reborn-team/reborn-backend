package com.reborn.reborn.security.domain;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * Token이 없거나 유효하지 않는 사용자는
 * Id 값은 0을 반환한다.
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? 0L : member.id")
public @interface LoginMember {
}