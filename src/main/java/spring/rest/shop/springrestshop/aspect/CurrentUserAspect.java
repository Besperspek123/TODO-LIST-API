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
        return userRepository.findByUsernameIgnoreCase(authentication.getName());
    }

    @Before("execution(* spring.rest.shop.springrestshop.controller.*.*(..)) ||"
            + "execution(* spring.rest.shop.springrestshop.restcontroller.*.*(..))")
    public void setCurrentUser(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsernameIgnoreCase(username);
            SecurityContext.setCurrentUser(user);
        }
    }


        @Before("@annotation(org.springframework.web.bind.annotation.PostMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void addCurrentUserToModel(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User currentUser = userRepository.findByUsernameIgnoreCase(authentication.getName());
            if (currentUser != null) {
                for (Object arg : joinPoint.getArgs()) {
                    if (arg instanceof Model) {
                        ((Model) arg).addAttribute("currentUser", currentUser);
                    }
                    else if (arg instanceof ModelAndView) {
                        ((ModelAndView) arg).addObject("currentUser", currentUser);
                    }
                }
            }
        }
    }
}
