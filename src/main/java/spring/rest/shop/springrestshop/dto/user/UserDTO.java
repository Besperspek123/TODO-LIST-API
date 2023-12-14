package spring.rest.shop.springrestshop.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import spring.rest.shop.springrestshop.entity.User;

@Data
@RequiredArgsConstructor
public class UserDTO {
    private long id;
    private String email;



}
