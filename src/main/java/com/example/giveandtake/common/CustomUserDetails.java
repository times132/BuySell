package com.example.giveandtake.common;
import com.example.giveandtake.model.entity.Role;
import com.example.giveandtake.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sun.jvm.hotspot.memory.SystemDictionary;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetails.class);

    private User user;
    private Collection<? extends GrantedAuthority> authorities;
    private Set<Role> authList;

    public static CustomUserDetails create(User user){
        Set<Role> authList = user.getRoles();
        System.out.println(authList);
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : authList) {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }
        return new CustomUserDetails(user, authorities, authList);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getNickname();
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
