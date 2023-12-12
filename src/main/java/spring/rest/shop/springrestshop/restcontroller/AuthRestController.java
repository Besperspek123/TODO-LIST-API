package spring.rest.shop.springrestshop.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
import spring.rest.shop.springrestshop.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Authorization", description = "The Authorization API")

public class AuthRestController {

    @Autowired
    AuthenticationManager authenticationManager;

    private final AuthService authService;


    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest loginRequest) {
        JwtResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "Register")
    public ResponseEntity<String> register(@Valid @RequestBody SignUpDto user) {
        System.out.println(user);
        authService.register(user);

        return new ResponseEntity<>("User with email: " + user.getEmail() + " has been created", HttpStatus.OK);
    }
}
