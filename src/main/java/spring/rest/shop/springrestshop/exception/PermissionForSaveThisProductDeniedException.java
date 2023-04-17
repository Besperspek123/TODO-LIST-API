package spring.rest.shop.springrestshop.exception;

public class PermissionForSaveThisProductDeniedException extends Exception {
    public PermissionForSaveThisProductDeniedException() {
        super();
    }
    public PermissionForSaveThisProductDeniedException(String message ) {
        super(message);
    }
}
