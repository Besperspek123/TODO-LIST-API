package spring.rest.shop.springrestshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
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


}
