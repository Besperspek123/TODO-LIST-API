package spring.rest.shop.springrestshop.exception;

public class UserWithThisMailAlreadyRegisteredException extends RuntimeException{
    public UserWithThisMailAlreadyRegisteredException() {
    }

    public UserWithThisMailAlreadyRegisteredException(String message) {
        super(message);
    }
}
