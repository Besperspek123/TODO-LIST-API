package spring.rest.shop.springrestshop.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import spring.rest.shop.springrestshop.exception.PermissionForSaveThisProductDeniedException;
import spring.rest.shop.springrestshop.exception.UnauthorizedShopAccessException;
import spring.rest.shop.springrestshop.exception.UserAlreadyRegisteredException;
import spring.rest.shop.springrestshop.exception.UserBannedException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UserBannedException.class)
    public RedirectView handleUserBannedException(UserBannedException exception){
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/registration");
        redirectView.addStaticAttribute("errorMessage", exception.getMessage());
        return redirectView;
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> userAlreadyRegisteredException(UserAlreadyRegisteredException exception){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<String> userTryAddProductInNotHerShop(UnauthorizedShopAccessException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<String> userTryEditNotHerProduct(PermissionForSaveThisProductDeniedException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.UNAUTHORIZED);

    }

}
