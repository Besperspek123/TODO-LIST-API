package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.dto.SignUpDto;
import spring.rest.shop.springrestshop.dto.auth.JwtRequest;
import spring.rest.shop.springrestshop.dto.auth.JwtResponse;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.UserAlreadyRegisteredException;
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

    public void register(SignUpDto user) throws UserAlreadyRegisteredException {
        if(userService.findUserByUsername(user.getUsername())!=null)
        {
            throw new UserAlreadyRegisteredException("User with this username already registered");
        }
        User userForRegistration= new User();
        userForRegistration.setUsername(user.getUsername());
        userForRegistration.setPassword(user.getPassword());
        userForRegistration.setPasswordConfirm(user.getConfirmPassword());
        userForRegistration.setEmail(user.getEmail());
        userService.saveUser(userForRegistration);

    }
}
