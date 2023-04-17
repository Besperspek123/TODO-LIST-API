package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.entity.*;
import spring.rest.shop.springrestshop.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers(){
        return userRepository.findAllBy();
    }

    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean saveUser(User user) {
        if(userRepository.findByUsername(user.getUsername())==null){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.getRoles().add(Role.ROLE_USER);
            user.setActivity(true);
            if (user.getUsername().equals("admin")){
                user.getRoles().add(Role.ROLE_ADMIN);
            }
            Cart cart = new Cart();
            cartRepository.save(cart); // сохраняем корзину
            cart.setBuyer(user);
            user.setCart(cart);
            userRepository.save(user); // сохраняем пользователя
            log.info("Saving new User with username: {}", user.getUsername());
            return true;
        }
        else return false;
    }

}

