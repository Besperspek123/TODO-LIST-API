package spring.rest.shop.springrestshop.exception_handling_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;
import spring.rest.shop.springrestshop.exception.*;

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
    public ResponseEntity<UserIncorrectData> UserWithThisMailAlreadyRegistered(UserWithThisMailAlreadyRegisteredException exception){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> userAlreadyBannedException(UserAlreadyBannedException exception){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> UserPasswordAndConfirmPasswordIsDifferentException(UserPasswordAndConfirmPasswordIsDifferentException exception){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> userNotBanned(UserNotBannedException exception){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> cartEmpty(CartEmptyException exception){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<String> userTryGetEntityThatDoesNotExist(EntityNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<String> userTrySendNotificationMessageWithEmptyTitleOrMessage(EmptyFieldException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> userTryAddProductInNotHerShop(UnauthorizedShopAccessException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<String> userTryEditNotHerProduct(PermissionForSaveThisProductDeniedException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.UNAUTHORIZED);

    }
    @ExceptionHandler
    public ResponseEntity<String> userTryAddReviewInProductWhereDontBuy(AccessDeniedException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.UNAUTHORIZED);

    }

}
