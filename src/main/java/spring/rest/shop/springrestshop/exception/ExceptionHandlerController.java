package spring.rest.shop.springrestshop.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UserBannedException.class)
    public RedirectView handleUserBannedException(UserBannedException exception){
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/registration");
        redirectView.addStaticAttribute("errorMessage", exception.getMessage());
        return redirectView;
    }

}
