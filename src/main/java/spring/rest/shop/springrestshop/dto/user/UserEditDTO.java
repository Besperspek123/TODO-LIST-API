package spring.rest.shop.springrestshop.dto.user;

import lombok.Data;
import spring.rest.shop.springrestshop.entity.User;

@Data
public class UserEditDTO {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    public UserEditDTO(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
