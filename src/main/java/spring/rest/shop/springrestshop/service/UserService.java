package spring.rest.shop.springrestshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.entity.Role;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.*;
import spring.rest.shop.springrestshop.jwt.JwtEntityFactory;
import spring.rest.shop.springrestshop.repository.UserRepository;


import java.util.List;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return JwtEntityFactory.create(user);                   
                                                            
    }
    public boolean checkIfUserExistsByUsername(String email){
        User user = userRepository.findByEmailIgnoreCase(email);
        if(user == null){
            return false;
        }
        else return true;
    }
    public boolean checkIfUserExistsByEmail(String email){
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null){
            return false;
        }
        else return true;
    }


    public User getUserById(long id){
       return userRepository.findById(id);
    }
    public User getUserByEmail(String email){
        return userRepository.findByEmailIgnoreCase(email);
    }

    public List<User> getAllUsers(){
        return userRepository.findAllBy();
    }

    public User findUserByUsername(String email) {
        User user = userRepository.findByEmailIgnoreCase(email);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    public void saveNewUser(User user) {
        if(user.getId() == null)
        {

            if(user.getPassword() == null){
                throw new NullPointerException("Password cant be null");
            }
            if(user.getPassword().isEmpty()){
                throw new PasswordCantBeEmptyException("Your password can`t be empty");
            }
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            if(checkIfUserExistsByUsername(user.getUsername())){
                throw new UserAlreadyRegisteredException("User with this email already registered");
            }
            if (user.getUsername().equals("admin")){
                user.getRoles().add(Role.ROLE_ADMIN);
            }
            else user.getRoles().add(Role.ROLE_USER);
        }

        userRepository.save(user); // сохраняем пользователя
        log.info("Saving User with mail: {}", user.getUsername());
    }





}

