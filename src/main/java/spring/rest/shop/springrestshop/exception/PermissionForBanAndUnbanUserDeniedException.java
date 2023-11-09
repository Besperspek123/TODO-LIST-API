package spring.rest.shop.springrestshop.exception;

public class PermissionForBanAndUnbanUserDeniedException extends RuntimeException {
    public PermissionForBanAndUnbanUserDeniedException() {
        super();
    }
    public PermissionForBanAndUnbanUserDeniedException(String message ) {
        super(message);
    }
}
