package spring.rest.shop.springrestshop.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER,ROLE_ADMIN,ROLE_ADMINISTRATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
