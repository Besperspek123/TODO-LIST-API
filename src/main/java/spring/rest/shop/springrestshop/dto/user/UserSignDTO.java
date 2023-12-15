package spring.rest.shop.springrestshop.dto.user;

import lombok.Data;

@Data
public class UserSignDTO {
    private String email;
    private String password;

    public UserSignDTO(String email,String password){
        this.email = email;
        this.password = password;
    }

}
