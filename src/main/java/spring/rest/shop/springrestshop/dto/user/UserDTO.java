package spring.rest.shop.springrestshop.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import spring.rest.shop.springrestshop.entity.User;

@Data
@RequiredArgsConstructor
public class UserDTO {
    @Schema(description = "ID of the user. Either 'id' or 'email' must be provided, not both.",
            example = "123", required = false)
    private long id;

    @Schema(description = "Email of the user. Either 'email' or 'id' must be provided, not both.",
            example = "user@example.com", required = false)
    private String email;


}
