package spring.rest.shop.springrestshop.exception;

public class UserPasswordAndConfirmPasswordIsDifferentException extends RuntimeException{
    public UserPasswordAndConfirmPasswordIsDifferentException() {
    }

    public UserPasswordAndConfirmPasswordIsDifferentException(String message) {
        super(message);
    }
}
