package spring.rest.shop.springrestshop.restcontroller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.rest.shop.springrestshop.dto.SignUpDto;
import spring.rest.shop.springrestshop.dto.auth.JwtRequest;
import spring.rest.shop.springrestshop.dto.auth.JwtResponse;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.repository.UserRepository;
import spring.rest.shop.springrestshop.service.AuthService;
import spring.rest.shop.springrestshop.service.UserService;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

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
public User register(@Validated @RequestBody SignUpDto user){

    try {
        authService.register(user);
    }
    catch (Exception e){

        System.out.println(e.getMessage());
    }
    return userService.findUserByUsername(user.getUsername());
}
}
