package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.dto.auth.SignUpDto;
import spring.rest.shop.springrestshop.dto.jwt.JwtRequest;
import spring.rest.shop.springrestshop.dto.jwt.JwtResponse;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.*;
import spring.rest.shop.springrestshop.jwt.JwtTokenProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = userService.findUserByUsername(loginRequest.getUsername());
        System.out.println(user.getUsername());
        jwtResponse.setId(user.getId());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getRoles()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(), user.getUsername()));
        return jwtResponse;
    }


    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }

    public void register(SignUpDto user) {
        if(user.getEmail() == null || user.getEmail().isEmpty()){
            throw new EmailIsNullOrEmptyException("Email cant be empty or null");
        }
        if(user.getPassword() == null || user.getPassword().isEmpty()){
            throw new PasswordCantBeEmptyException("Password cant be empty or null");
        }

        final Pattern emailPattern =
                Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        final Matcher matcher = emailPattern.matcher(user.getEmail());
        if(!matcher.matches()){
            throw new InvalidEmailFormatException("You try register user with invalid email, try again with correct email");
        }
        if(userService.checkIfUserExistsByUsername(user.getEmail()))
        {
            throw new UserAlreadyRegisteredException("User with username: "+ user.getEmail() +" already registered");
        }
        User userForRegistration= new User();
        userForRegistration.setEmail(user.getEmail());
        userForRegistration.setPassword(user.getPassword());

        if(userService.checkIfUserExistsByEmail(user.getEmail())){
            throw new UserWithThisMailAlreadyRegisteredException("User with email: "+ user.getEmail() +" already registered");
        }
        userForRegistration.setEmail(user.getEmail());

        userService.saveNewUser(userForRegistration);

    }
}
