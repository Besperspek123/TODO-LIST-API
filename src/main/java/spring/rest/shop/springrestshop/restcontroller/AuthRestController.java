package spring.rest.shop.springrestshop.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.rest.shop.springrestshop.dto.auth.SignUpDto;
import spring.rest.shop.springrestshop.dto.jwt.JwtRequest;
import spring.rest.shop.springrestshop.dto.jwt.JwtResponse;
import spring.rest.shop.springrestshop.exception.UserAlreadyRegisteredException;
import spring.rest.shop.springrestshop.exception.UserPasswordAndConfirmPasswordIsDifferentException;
import spring.rest.shop.springrestshop.exception.UserWithThisMailAlreadyRegisteredException;
import spring.rest.shop.springrestshop.repository.UserRepository;
import spring.rest.shop.springrestshop.service.AuthService;
import spring.rest.shop.springrestshop.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController{

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    private final AuthService authService;

@PostMapping("/login")
public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
    return authService.login(loginRequest);
}

@PostMapping("/register")
public ResponseEntity<String> register(@Validated @RequestBody SignUpDto user) throws UserAlreadyRegisteredException, UserWithThisMailAlreadyRegisteredException, UserPasswordAndConfirmPasswordIsDifferentException {

        authService.register(user);

    return new ResponseEntity<>("User with nickname: " + user.getUsername()  + " has been created",HttpStatus.ACCEPTED);
}
}
