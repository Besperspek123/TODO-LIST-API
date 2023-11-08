package spring.rest.shop.springrestshop.exception;

public class PermissionForSaveThisProductDeniedException extends RuntimeException {
    public PermissionForSaveThisProductDeniedException() {
        super();
    }
    public PermissionForSaveThisProductDeniedException(String message ) {
        super(message);
    }
}
