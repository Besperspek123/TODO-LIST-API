package spring.rest.shop.springrestshop.dto.auth;

import lombok.Data;
import spring.rest.shop.springrestshop.entity.Role;

import java.util.Set;

@Data
@Deprecated
public class EditUserDTO {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private long Balance;
    private Set<Role> roles;

}
