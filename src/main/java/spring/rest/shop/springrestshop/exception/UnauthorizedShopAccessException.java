package spring.rest.shop.springrestshop.exception;

public class UnauthorizedShopAccessException extends RuntimeException {
    public UnauthorizedShopAccessException() {
        super();
    }
    public UnauthorizedShopAccessException(String message ) {
        super(message);
    }
}
