package spring.rest.shop.springrestshop.exception;

public class UserNotBannedException extends Exception {
    public UserNotBannedException() {
        super();
    }
    public UserNotBannedException(String message ) {
        super(message);
    }
}
