package spring.rest.shop.springrestshop.exception;

public class UserAlreadyHasThisTaskInHisTaskListException extends RuntimeException{
    public UserAlreadyHasThisTaskInHisTaskListException() {
    }

    public UserAlreadyHasThisTaskInHisTaskListException(String message) {
        super(message);
    }
}
