package spring.rest.shop.springrestshop.restcontroller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "User",description = "The User API")
public class UserRestController {

    private final UserService userService;




}
