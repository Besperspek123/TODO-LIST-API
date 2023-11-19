package spring.rest.shop.springrestshop.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public enum Role implements GrantedAuthority {
    ROLE_USER,ROLE_ADMIN,ROLE_ADMINISTRATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
