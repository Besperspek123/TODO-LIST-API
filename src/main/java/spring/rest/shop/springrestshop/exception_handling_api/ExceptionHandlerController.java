package spring.rest.shop.springrestshop.exception_handling_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import spring.rest.shop.springrestshop.exception.*;

@ControllerAdvice
public class ExceptionHandlerController {


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
    public ResponseEntity<UserIncorrectData> PasswordCantBeEmptyException(PasswordCantBeEmptyException exception){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<String> userTryToRegisterWithInvalidEmail(InvalidEmailFormatException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler
    public ResponseEntity<String> userTryAddReviewInProductWhereDontBuy(AccessDeniedException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.FORBIDDEN);

    }
    @ExceptionHandler
    public ResponseEntity<String> userTryToRegisterWithEmptyEmail(EmailIsNullOrEmptyException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);

    }
    @ExceptionHandler
    public ResponseEntity<String> entityNotFound(EntityNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler
    public ResponseEntity<String> userNotFound(UserNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler
    public ResponseEntity<String> userAlreadyHasThisTaskInHisTaskList(UserAlreadyHasThisTaskInHisTaskListException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);

    }

}
