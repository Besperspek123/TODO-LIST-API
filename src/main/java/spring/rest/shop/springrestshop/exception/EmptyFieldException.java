package spring.rest.shop.springrestshop.exception;

public class EmptyFieldException extends Exception {
    public EmptyFieldException() {
        super();
    }
    public EmptyFieldException(String message ) {
        super(message);
    }
}
