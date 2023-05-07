package spring.rest.shop.springrestshop.dto.user;

import lombok.Data;
import spring.rest.shop.springrestshop.entity.User;

@Data
public class UserDTO {
    private long id;
    private String name;
    private String email;
    private long Balance;

    public UserDTO(User user){
        this.id = user.getId();
        this.name = user.getUsername();
        this.email = user.getEmail();
        this.Balance = user.getBalance();

    }

}
