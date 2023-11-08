package spring.rest.shop.springrestshop.exception;

public class ProductAlreadyHaveInCartException extends RuntimeException {
    public ProductAlreadyHaveInCartException() {
        super();
    }
    public ProductAlreadyHaveInCartException(String message ) {
        super(message);
    }
}
