package spring.rest.shop.springrestshop.exception;

public class EmailIsNullOrEmptyException extends RuntimeException {
    public EmailIsNullOrEmptyException() {
        super();
    }

    public EmailIsNullOrEmptyException(String message) {
        super(message);
    }
}
