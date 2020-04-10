package com.example.giveandtake.common;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.model.entity.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetails.class);

    private User user;
    private Collection<? extends GrantedAuthority> authorities;

    public static CustomUserDetails create(User user){
        Set<UserRoles> authList = user.getRoles();
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (UserRoles role: authList) {
            authorities.add(new SimpleGrantedAuthority(role.getRole().getName().name()));
            System.out.println(role.getRole().getName().name());
        }
        return new CustomUserDetails(user, authorities);
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
