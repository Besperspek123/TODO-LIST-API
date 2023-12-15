package spring.rest.shop.springrestshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.repository.UserRepository;

@Aspect
@Component
public class CurrentUserAspect {
    @Autowired
    UserRepository userRepository;

    public User getCurrentUser(Authentication authentication) {
        return userRepository.findByEmailIgnoreCase(authentication.getName());
    }

    @Before("execution(* spring.rest.shop.springrestshop.restcontroller.*.*(..))")
    public void setCurrentUser(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmailIgnoreCase(username);
            SecurityContext.setCurrentUser(user);
        }
    }


}
