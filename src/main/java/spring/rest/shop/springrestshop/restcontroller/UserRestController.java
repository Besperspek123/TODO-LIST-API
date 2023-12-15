package spring.rest.shop.springrestshop.restcontroller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.rest.shop.springrestshop.dto.user.UserDetailsDTO;
import spring.rest.shop.springrestshop.service.UserService;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Hidden
public class UserRestController {

    private final UserService userService;


    @GetMapping("/users/{userID}")
    public ResponseEntity<UserDetailsDTO> getInfoAboutUser(@PathVariable long userID){
        return new ResponseEntity<>(new UserDetailsDTO(userService.getUserById(userID)), HttpStatus.OK);
    }




}
