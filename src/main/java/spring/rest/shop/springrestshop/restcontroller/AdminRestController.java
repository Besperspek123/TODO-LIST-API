package spring.rest.shop.springrestshop.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.notification.NotificationDTO;
import spring.rest.shop.springrestshop.dto.review.ReviewDTO;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.dto.user.UserDetailsDTO;
import spring.rest.shop.springrestshop.dto.user.UserEditDTO;
import spring.rest.shop.springrestshop.entity.Notification;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.*;
import spring.rest.shop.springrestshop.service.NotificationService;
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

    private final NotificationService notificationService;
    private final UserService userService;

    @Operation(summary = "Get all Users")
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> usersList = userService.getAllUsers().stream().map(UserDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(usersList,HttpStatus.OK);

    }
    @GetMapping("/users/{userId}")
    @Operation(summary = "Get info about the user")
    public ResponseEntity<UserDetailsDTO> getUserInfo(@PathVariable long userId){
        return new ResponseEntity<>(new UserDetailsDTO(userService.getUserById(userId)),HttpStatus.OK);

    }

    @Operation(summary = "Edit the user")
    @PutMapping("/users/{userId}")

    public ResponseEntity<UserDetailsDTO> editUser (@PathVariable long userId, @RequestBody UserEditDTO user, Model model) throws EntityNotFoundException, UserAlreadyRegisteredException {
        userService.editUser(userId,user);
        return new ResponseEntity<>(new UserDetailsDTO(userService.getUserById(userId)),HttpStatus.OK);

    }

    @Operation(summary = "Ban the user")
    @PostMapping("/users/{userId}/ban")
    public ResponseEntity<String> banUser(@PathVariable long userId) throws UserAlreadyBannedException {
        User user = userService.getUserById(userId);
        userService.banUser(userService.getUserById(userId));
        return new ResponseEntity<>("User with username: " + user.getUsername() + " is banned",HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/unban")
    @Operation(summary = "Unban the user")
    public ResponseEntity<String> unbanUser(@PathVariable long userId) throws UserNotBannedException {
        User user = userService.getUserById(userId);
        userService.unbanUser(userService.getUserById(userId));
        return new ResponseEntity<>("User with username: " + user.getUsername() + " is unbanned",HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/balance/{count}")
    @Operation(summary = "Add balance")
    public ResponseEntity<UserDetailsDTO> addBalance(@PathVariable long count,@PathVariable long userId){
        userService.addBalance(userId,count);

    return new ResponseEntity<>(new UserDetailsDTO(userService.getUserById(userId)),HttpStatus.OK);

    }

    @PostMapping("/notifications/{userId}")
    @Operation(summary = "Send notification to User")
    public ResponseEntity<String> sendNotification(@PathVariable long userId, @RequestBody NotificationDTO notificationDTO) throws EntityNotFoundException, EmptyFieldException {
        notificationService.sendMessage(userId,notificationDTO);
        return new ResponseEntity<>("Notification has been send",HttpStatus.OK);
    }

}
