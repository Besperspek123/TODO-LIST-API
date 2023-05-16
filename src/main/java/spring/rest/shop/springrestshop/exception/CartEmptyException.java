package spring.rest.shop.springrestshop.exception;

public class CartEmptyException extends Exception {
    public CartEmptyException() {
        super();
    }
    public CartEmptyException(String message ) {
        super(message);
    }
}
