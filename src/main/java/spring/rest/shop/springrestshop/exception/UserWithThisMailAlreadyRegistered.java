package spring.rest.shop.springrestshop.exception;

public class UserWithThisMailAlreadyRegistered extends RuntimeException{
    public UserWithThisMailAlreadyRegistered() {
    }

    public UserWithThisMailAlreadyRegistered(String message) {
        super(message);
    }
}
