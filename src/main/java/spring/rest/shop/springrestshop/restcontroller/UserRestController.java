package spring.rest.shop.springrestshop.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.service.UserService;

import java.util.List;

@RestController
public class UserRestController extends BaseRestController{

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();

    }
}
