package spring.rest.shop.springrestshop.exception;

public class InvalidFieldDataException extends RuntimeException{
    public InvalidFieldDataException() {
    }

    public InvalidFieldDataException(String message) {
        super(message);
    }
}
