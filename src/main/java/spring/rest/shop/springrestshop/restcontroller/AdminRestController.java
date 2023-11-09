package spring.rest.shop.springrestshop.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.dto.user.UserDetailsDTO;
import spring.rest.shop.springrestshop.dto.user.UserEditDTO;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.*;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ShopService;
import spring.rest.shop.springrestshop.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Tag(name = "Administration",description = "The Administration API")
public class AdminRestController {

    private final ProductService productService;
    private final ShopService shopService;
    private final UserService userService;

    @Operation(summary = "Get all Users",tags = "administration")
    @GetMapping("/users")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers().stream().map(UserDTO::new).collect(Collectors.toList());

    }
    @GetMapping("/users/{userId}")
    @Operation(summary = "Get info about the user",tags = "administration")
    public UserDetailsDTO getUserInfo(@PathVariable long userId){
        return new UserDetailsDTO(userService.getUserById(userId));

    }

    @Operation(summary = "Edit the user",tags = "administration")
    @PutMapping("/users/{userId}")

    public UserDetailsDTO editUser (@PathVariable long userId, @RequestBody UserEditDTO user, Model model) throws EntityNotFoundException, UserAlreadyRegisteredException {


        userService.editUser(userId,user);
        return new UserDetailsDTO(userService.getUserById(userId));

    }

    @Operation(summary = "Ban the user",tags = "administration")
    @PostMapping("/users/{userId}/ban")
    public ResponseEntity<String> banUser(@PathVariable long userId) throws UserAlreadyBannedException {
        User user = userService.getUserById(userId);
        userService.banUser(userService.getUserById(userId));
        return new ResponseEntity<>("User with username: " + user.getUsername() + " is banned",HttpStatus.ACCEPTED);
    }

    @PostMapping("/users/{userId}/unban")
    @Operation(summary = "Unban the user",tags = "administration")
    public ResponseEntity<String> unbanUser(@PathVariable long userId) throws UserNotBannedException {
        User user = userService.getUserById(userId);
        userService.unbanUser(userService.getUserById(userId));
        return new ResponseEntity<>("User with username: " + user.getUsername() + " is unbanned",HttpStatus.ACCEPTED);
    }

    @PostMapping("/users/{userId}/balance/{count}")
    @Operation(summary = "Add balance",tags = "administration")
    public UserDetailsDTO addBalance(@PathVariable long count,@PathVariable long userId){
        User user = userService.getUserById(userId);
        if(user== null){
            throw new UsernameNotFoundException("Don`t have user with ID: " + userId);
        }
            userService.addBalance(user,count);
            return new UserDetailsDTO(user);



    }

}
