package spring.rest.shop.springrestshop.dto.user;

import lombok.Data;
import spring.rest.shop.springrestshop.entity.User;

@Data
public class UserSignDTO {
    private String email;
    private String password;

    public UserSignDTO(String email,String password){
        this.email = email;
        this.password = password;
    }

}
