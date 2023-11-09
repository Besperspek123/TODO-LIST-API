package spring.rest.shop.springrestshop.exception;

public class PasswordCantBeEmptyException extends RuntimeException{
    public PasswordCantBeEmptyException() {
    }

    public PasswordCantBeEmptyException(String message) {
        super(message);
    }
}
