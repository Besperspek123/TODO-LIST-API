package spring.rest.shop.springrestshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.dto.user.UserEditDTO;
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
    public UserDetails loadUserByUsername(String username) {    
        User user = userRepository.findByUsernameIgnoreCase(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return JwtEntityFactory.create(user);                   
                                                            
    }
    public boolean checkIfUserExistsByUsername(String username){
        User user = userRepository.findByUsernameIgnoreCase(username);
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

    public List<User> getAllUsers(){
        return userRepository.findAllBy();
    }

    public User findUserByUsername(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    public List<User> findUsersByUsernameContaining(String string){
        return userRepository.findByUsernameContaining(string);
    }
    public void editUser(User givenUser) {
            if(!getUserById(givenUser.getId()).getUsername().equals(givenUser.getUsername())){
                if (checkIfUserExistsByUsername(givenUser.getUsername())){
                    throw new UserAlreadyRegisteredException("Username with this name is already registered");
                }
            }
        if(!getUserById(givenUser.getId()).getEmail().equals(givenUser.getEmail())){
            if (checkIfUserExistsByEmail(givenUser.getEmail())){
                throw new UserAlreadyRegisteredException("Username with this Mail is already registered");
            }
        }
        if (!givenUser.getPassword().equals(givenUser.getPasswordConfirm())) {
            throw new UserPasswordAndConfirmPasswordIsDifferentException("Your password and confirm password are different");
        }
            if(givenUser.getPassword() == null || givenUser.getPassword().isEmpty()){
                givenUser.setPassword(userRepository.findById((long) givenUser.getId()).getPassword());
            }
                else givenUser.setPassword(bCryptPasswordEncoder.encode(givenUser.getPassword()));

            givenUser.setActivity(true);
            givenUser.setPasswordConfirm("");
        userRepository.save(givenUser);
        log.info("Edited User with username: {}", givenUser.getUsername());
    }

    //TODO new write the only one method for edit user. Only one for controller and rest controller
    public void editUser(long userId, UserEditDTO editUser) throws EntityNotFoundException, UserAlreadyRegisteredException {
        if(userRepository.findById(userId) == null){
            throw new EntityNotFoundException("Username with ID " + userId + " not found");
        }
        User user = userRepository.findById(userId);
        if(editUser.getUsername() != null){
            if(userRepository.findByUsernameIgnoreCase(editUser.getUsername()) != null){
                throw new UserAlreadyRegisteredException("Username: "+ editUser.getUsername() +" already exist");
            }
            user.setUsername(editUser.getUsername());
        }
        if(editUser.getEmail() != null){
            if(userRepository.findByEmailIgnoreCase(editUser.getEmail()) != null){
                throw new UserAlreadyRegisteredException("Email: " + editUser.getEmail() + " already exist");
            }
            user.setEmail(editUser.getEmail());
        }
        if(editUser.getPassword() != null){
            user.setPassword(editUser.getPassword());
        }

        userRepository.save(user);
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
            user.setActivity(true);

            if(checkIfUserExistsByUsername(user.getUsername())){
                throw new UserAlreadyRegisteredException("User with this username already registered");
            }
            if (user.getUsername().equals("admin")){
                user.getRoles().add(Role.ROLE_ADMIN);
            }
            else user.getRoles().add(Role.ROLE_USER);
            user.setPasswordConfirm("");
        }

        userRepository.save(user); // сохраняем пользователя
        log.info("Saving User with username: {}", user.getUsername());
    }





}

