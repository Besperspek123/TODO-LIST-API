package spring.rest.shop.springrestshop.dto.user;

import lombok.Data;
import spring.rest.shop.springrestshop.entity.User;

@Data
public class UserDTO {
    private long id;
    private String email;

    public UserDTO(User user){
        this.id = user.getId();
        this.email = user.getEmail();

    }

}
