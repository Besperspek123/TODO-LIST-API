package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.dto.auth.SignUpDto;
import spring.rest.shop.springrestshop.dto.jwt.JwtRequest;
import spring.rest.shop.springrestshop.dto.jwt.JwtResponse;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.UserAlreadyRegisteredException;
import spring.rest.shop.springrestshop.exception.UserPasswordAndConfirmPasswordIsDifferentException;
import spring.rest.shop.springrestshop.exception.UserWithThisMailAlreadyRegistered;
import spring.rest.shop.springrestshop.jwt.JwtTokenProvider;

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

    public void register(SignUpDto user) throws UserAlreadyRegisteredException, UserWithThisMailAlreadyRegistered, UserPasswordAndConfirmPasswordIsDifferentException {
        User userForRegistration= new User();

        if(userService.checkIfUserExistsByUsername(user.getUsername()))
        {
            throw new UserAlreadyRegisteredException("User with username: "+ user.getUsername() +" already registered");
        }
        userForRegistration.setUsername(user.getUsername());


        if (!user.getPassword().equals(user.getConfirmPassword())){
            throw new UserPasswordAndConfirmPasswordIsDifferentException("Пароли не совпадают");
        }
        userForRegistration.setPassword(user.getPassword());
        userForRegistration.setPasswordConfirm(user.getConfirmPassword());

        if(userService.checkIfUserExistsByEmail(user.getEmail())){
            throw new UserWithThisMailAlreadyRegistered("User with email: "+ user.getEmail() +" already registered");
        }
        userForRegistration.setEmail(user.getEmail());

        userService.saveNewUser(userForRegistration);

    }
}
