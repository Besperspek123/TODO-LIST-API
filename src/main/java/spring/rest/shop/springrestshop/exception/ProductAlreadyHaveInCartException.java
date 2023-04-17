package spring.rest.shop.springrestshop.exception;

public class ProductAlreadyHaveInCartException extends Exception {
    public ProductAlreadyHaveInCartException() {
        super();
    }
    public ProductAlreadyHaveInCartException(String message ) {
        super(message);
    }
}
