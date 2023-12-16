package spring.rest.shop.springrestshop.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import spring.rest.shop.springrestshop.entity.User;

@Data
public class UserDTO {

    public UserDTO(){}

    public UserDTO(User user){
        this.email = user.getEmail();
        this.id = user.getId();
    }

    @Schema(description = "ID of the user. Either 'id' or 'email' must be provided, not both.",
            example = "123")
    private long id;

    @Schema(description = "Email of the user. Either 'email' or 'id' must be provided, not both.",
            example = "user@example.com")
    private String email;


}
