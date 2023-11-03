package spring.rest.shop.springrestshop.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;

public class UserBannedException extends InternalAuthenticationServiceException {
    public UserBannedException(String message) {
        super(message);
    }
}
