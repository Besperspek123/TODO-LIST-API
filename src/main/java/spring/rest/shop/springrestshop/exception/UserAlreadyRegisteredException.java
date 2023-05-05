package spring.rest.shop.springrestshop.exception;

public class UserAlreadyRegisteredException extends Exception {
    public UserAlreadyRegisteredException() {
        super();
    }
    public UserAlreadyRegisteredException(String message ) {
        super(message);
    }
}
