package com.example.giveandtake.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class KakaoDTO implements OAuth2User {
    private List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_SOCIAL");
    private String id;
    @JsonProperty("kakao_account")
    private Map<String, Object> kakaoAccount;
    private Map<String, Object> properties;

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        if (kakaoAccount.get("email_needs_agreement").equals(false)){
            attributes.put("email", kakaoAccount.get("email"));
        }
        attributes.put("id", "KA_" + this.id);
        attributes.put("name", properties.get("nickname"));

        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return "KA_" + this.id;
    }
}
