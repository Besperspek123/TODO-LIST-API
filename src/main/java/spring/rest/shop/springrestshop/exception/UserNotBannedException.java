package spring.rest.shop.springrestshop.exception;

public class UserNotBannedException extends RuntimeException {
    public UserNotBannedException() {
        super();
    }
    public UserNotBannedException(String message ) {
        super(message);
    }
}
