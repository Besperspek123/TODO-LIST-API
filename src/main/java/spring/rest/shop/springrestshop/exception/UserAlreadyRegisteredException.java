package spring.rest.shop.springrestshop.exception;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException() {
        super();
    }
    public UserAlreadyRegisteredException(String message ) {
        super(message);
    }
}
