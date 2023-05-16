package spring.rest.shop.springrestshop.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.dto.user.UserDetailsDTO;
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

    public UserDetailsDTO editUser (@PathVariable long userId, @RequestBody User user, Model model) throws EntityNotFoundException, UserAlreadyRegisteredException {


        userService.editUser(userId,user);
        return new UserDetailsDTO(userService.getUserById(userId));

    }

    @PostMapping("/users/{userId}/ban")
    public ResponseEntity<String> banUser(@PathVariable long userId) throws UserAlreadyBannedException {
        User user = userService.getUserById(userId);
        userService.banUser(userService.getUserById(userId));
        return new ResponseEntity<>("User with username: " + user.getUsername() + " is banned",HttpStatus.ACCEPTED);
    }

    @PostMapping("/users/{userId}/unban")
    public ResponseEntity<String> unbanUser(@PathVariable long userId) throws UserNotBannedException {
        User user = userService.getUserById(userId);
        userService.unbanUser(userService.getUserById(userId));
        return new ResponseEntity<>("User with username: " + user.getUsername() + " is unbanned",HttpStatus.ACCEPTED);
    }

    @PostMapping("/users/{userId}/balance/{count}")
    public UserDetailsDTO addBalance(@PathVariable long count,@PathVariable long userId){
        User user = userService.getUserById(userId);
        if(user== null){
            throw new UsernameNotFoundException("Don`t have user with ID: " + userId);
        }
            userService.addBalance(user,count);
            return new UserDetailsDTO(user);



    }

}
