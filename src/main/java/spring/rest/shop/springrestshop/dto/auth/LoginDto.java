package spring.rest.shop.springrestshop.dto.auth;

import lombok.Data;

@Data
@Deprecated
public class LoginDto {
    private String username;
    private String password;
}
