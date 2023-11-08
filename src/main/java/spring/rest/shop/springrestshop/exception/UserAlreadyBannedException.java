package spring.rest.shop.springrestshop.exception;

public class UserAlreadyBannedException extends RuntimeException {
    public UserAlreadyBannedException() {
        super();
    }
    public UserAlreadyBannedException(String message ) {
        super(message);
    }
}
