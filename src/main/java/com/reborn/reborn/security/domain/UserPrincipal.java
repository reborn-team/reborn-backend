package com.reborn.reborn.security.domain;

import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.member.domain.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class UserPrincipal implements UserDetails {

    private Member member;
    private Map<String, Object> attributes;

    public UserPrincipal(Member member) {
        this.member = member;
    }

    public static UserPrincipal create(Member member) {
        return new UserPrincipal(member);
    }

    public static UserPrincipal createMemberWithAttributes(Member member, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(member);
        userPrincipal.setAttr(attributes);

        return userPrincipal;
    }

    public Member getMember() {
        return member;
    }

    public void setAttr(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(MemberRole.USER.getKey()));
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
