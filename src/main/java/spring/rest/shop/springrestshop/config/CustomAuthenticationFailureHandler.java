package spring.rest.shop.springrestshop.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import spring.rest.shop.springrestshop.exception.UserBannedException;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        System.out.println(exception);
        if (exception instanceof UserBannedException) {
            setDefaultFailureUrl("/login?error=banned");
        } else {
            setDefaultFailureUrl("/login?error=invalid");
        }

        super.onAuthenticationFailure(request, response, exception);
    }
}
