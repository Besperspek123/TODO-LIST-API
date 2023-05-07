package spring.rest.shop.springrestshop.exception;

public class UnauthorizedShopAccessException extends Exception {
    public UnauthorizedShopAccessException() {
        super();
    }
    public UnauthorizedShopAccessException(String message ) {
        super(message);
    }
}
