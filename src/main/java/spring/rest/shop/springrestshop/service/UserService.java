package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.aspect.CurrentUserAspect;
import spring.rest.shop.springrestshop.entity.*;
import spring.rest.shop.springrestshop.exception.PermissionForBanAndUnbanUserDeniedException;
import spring.rest.shop.springrestshop.exception.UserBannedException;
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
    public boolean checkIfUserExistsByUsername(String username){
        if(userRepository.findByUsername(username) == null){
            return false;
        }
        return true;
    }
    public boolean checkIfUserExistsByEmail(String email){
        if(userRepository.findByEmail(email) == null){
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

    public boolean saveUser(User user) {
        if(userRepository.findByUsername(user.getUsername())==null
                && userRepository.findByEmail(user.getEmail())==null
                || userRepository.findByUsername(user.getUsername())!=null && user.getId() != null){
            if(user.getPassword() != null){
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }

            user.getRoles().add(Role.ROLE_USER);
            if (user.getActivity() == null){
                user.setActivity(true);
            }
            if (user.getUsername().equals("admin")){
                user.getRoles().add(Role.ROLE_ADMIN);
            }
            if(user.getCart() == null){
                Cart cart = new Cart();
                cartRepository.save(cart); // сохраняем корзину
                cart.setBuyer(user);
                user.setCart(cart);
            }

            user.setPasswordConfirm("");
            userRepository.save(user); // сохраняем пользователя
            log.info("Saving new User with username: {}", user.getUsername());
            return true;
        }
       return false;
    }
    public void banUser(User userForBan){
        User currentUser = userAspect.getCurrentUser(SecurityContextHolder.getContext().getAuthentication());
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

    public void unbanUser(User userForUnban,User currentUser){
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

    public void addBalance(User user, int deposit) {
        user.setBalance(user.getBalance() + deposit);
        userRepository.save(user);
    }
}

