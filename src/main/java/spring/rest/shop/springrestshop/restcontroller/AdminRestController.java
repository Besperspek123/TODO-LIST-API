package spring.rest.shop.springrestshop.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.dto.user.UserDetailsDTO;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.UnauthorizedShopAccessException;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ShopService;
import spring.rest.shop.springrestshop.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminRestController {

    private final ProductService productService;
    private final ShopService shopService;
    private final UserService userService;

    @GetMapping("/users")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers().stream().map(UserDTO::new).collect(Collectors.toList());

    }
    @GetMapping("/users/{userId}")
    public UserDetailsDTO getUserInfo(@PathVariable long userId){
        return new UserDetailsDTO(userService.getUserById(userId));

    }

    @PutMapping("/users/{userId}")

    public UserDetailsDTO editUser (@PathVariable long userId,@RequestBody User user){
        return null;
    }

}
