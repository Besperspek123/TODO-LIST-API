package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.aspect.CurrentUserAspect;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.entity.*;
import spring.rest.shop.springrestshop.exception.*;
import spring.rest.shop.springrestshop.jwt.JwtEntityFactory;
import spring.rest.shop.springrestshop.repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    private final CurrentUserAspect userAspect;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        if (!user.getActivity()) {
//            throw new UserBannedException("User is banned"); // Исключение при неактивном пользователе
//        }
        return JwtEntityFactory.create(user);

    }
    public boolean checkIfUserExistsByUsername(User user){
        if(user.getId() == null){
            if (userRepository.findByUsername(user.getUsername()) == null){
                return false;
            }

        }
        else if (user.getUsername().equals(userRepository.findById((long) user.getId()).getUsername()) ||
                !user.getUsername().equals(userRepository.findById((long) user.getId()).getUsername())
                && userRepository.findByUsername(user.getUsername()) == null){
            return false;
        }
        return true;
    }
    public boolean checkIfUserExistsByEmail(User user){
        if(user.getId() == null){
            if(userRepository.findByEmail(user.getEmail()) == null){
                return false;
            }
        }
        else if(user.getEmail().equals(userRepository.findById((long) user.getId()).getEmail())
                || !user.getEmail().equals(userRepository.findById((long) user.getId()).getEmail())
                && userRepository.findByEmail(user.getEmail()) == null){
            return false;
        }
        return true;
    }


    public User getUserById(long id){
       return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAllBy();
    }

    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> findUsersByUsernameContaining(String string){
        return userRepository.findByUsernameContaining(string);
    }
    public void editUser(User user) {
            if(user.getPassword() == null){
                user.setPassword(userRepository.findById((long) user.getId()).getPassword());
            }
                else user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            user.setActivity(true);
            user.setCart(userRepository.findById((long) user.getId()).getCart());
            user.setPasswordConfirm("");


        userRepository.save(user); // сохраняем пользователя
        log.info("Saving new User with username: {}", user.getUsername());
    }
    public void editUser(long userId, User editUser) throws EntityNotFoundException, UserAlreadyRegisteredException {
        if(userRepository.findById(userId) == null){
            throw new EntityNotFoundException("Username with ID " + userId + " not found");
        }
        User user = userRepository.findById(userId);
        if(editUser.getUsername() != null){
            if(userRepository.findByUsername(editUser.getUsername()) != null){
                throw new UserAlreadyRegisteredException("Username: "+ editUser.getUsername() +" already exist");
            }
            user.setUsername(editUser.getUsername());
        }
        if(editUser.getEmail() != null){
            if(userRepository.findByEmail(editUser.getEmail()) != null){
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
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActivity(true);
            user.getRoles().add(Role.ROLE_USER);
            if (user.getUsername().equals("admin")){
                user.getRoles().add(Role.ROLE_ADMIN);
            }
            Cart cart =new Cart();
            cartRepository.save(cart);
            cart.setBuyer(user);
            user.setCart(cart);
            user.setPasswordConfirm("");
        }

        userRepository.save(user); // сохраняем пользователя
        log.info("Saving new User with username: {}", user.getUsername());
    }
    public void banUser(User userForBan) throws UserAlreadyBannedException {
        User currentUser = SecurityContext.getCurrentUser();
        if(!userForBan.getActivity()){
            throw new UserAlreadyBannedException("User with username: " + userForBan.getUsername() + "already banned");
        }
        try {
            if(currentUser.getRoles().contains(Role.ROLE_ADMIN) && !userForBan.getRoles().contains(Role.ROLE_ADMIN)){
                userForBan.setActivity(false);
                userRepository.save(userForBan);
            }
            else throw new PermissionForBanAndUnbanUserDeniedException("You don`t have permissions for ban/unban users");
        }
        catch (PermissionForBanAndUnbanUserDeniedException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void unbanUser(User userForUnban) throws UserNotBannedException {
        User currentUser = SecurityContext.getCurrentUser();
        if(userForUnban.getActivity()){
            throw new UserNotBannedException("User with username: " + userForUnban.getUsername() + "is not banned");
        }
        try {
            if(currentUser.getRoles().contains(Role.ROLE_ADMIN)){
                userForUnban.setActivity(true);
                userRepository.save(userForUnban);
            }
            else throw new PermissionForBanAndUnbanUserDeniedException("You don`t have permissions for ban/unban users");
        }
        catch (PermissionForBanAndUnbanUserDeniedException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void addBalance(User user, long deposit) {
        if(!SecurityContext.getCurrentUser().getRoles().contains(Role.ROLE_ADMIN)){
            throw new AccessDeniedException("You don`t have permission");
        }
        user.setBalance(user.getBalance() + deposit);
        userRepository.save(user);
    }
}

