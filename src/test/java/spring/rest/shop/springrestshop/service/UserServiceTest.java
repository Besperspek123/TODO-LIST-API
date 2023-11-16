package spring.rest.shop.springrestshop.service;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import spring.rest.shop.springrestshop.entity.Cart;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.UserAlreadyRegisteredException;
import spring.rest.shop.springrestshop.exception.UserBannedException;
import spring.rest.shop.springrestshop.exception.UserNotFoundException;
import spring.rest.shop.springrestshop.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    UserService userService;

    @Test
    void loadUserByUsername_UserFoundAndActive_ReturnsUserDetails() {
        // Arrange
        String username = "testUser";
        User user = new User();
        user.setActivity(true);
        user.setId(1L);
        when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(user);

        // Act
        UserDetails result = userService.loadUserByUsername(username);

        // Assert
        assertNotNull(result);
        verify(userRepository).findByUsernameIgnoreCase(username);
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowException() {
        String username = "user";
        when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.loadUserByUsername(username));
    }
    @Test
    void loadUserByUsername_UserBannedException_ThrowException(){
        String username = "user";
        User user = new User();
        user.setUsername(username);
        user.setActivity(false);

        when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(user);
        assertThrows(UserBannedException.class, () -> userService.loadUserByUsername(username));

    }

    @Test
    void givenExistingUserName_whenCheckIfUserExistsByUsername_thenReturnTrue() {
        User user = new User();
        user.setUsername("user");
        when(userRepository.findByUsernameIgnoreCase(user.getUsername())).thenReturn(user);
        boolean isExist = userService.checkIfUserExistsByUsername(user.getUsername());

        assertTrue(isExist,"Пользователь должен существовать");
        verify(userRepository).findByUsernameIgnoreCase(user.getUsername());


    }

    @Test
    void givenNotExistingUserName_whenCheckIfUserExistsByUsername_thenReturnFalse() {
        User user = new User();
        user.setUsername("user");
        when(userRepository.findByUsernameIgnoreCase(user.getUsername())).thenReturn(null);
        boolean isExist = userService.checkIfUserExistsByUsername(user.getUsername());

        assertFalse(isExist,"Пользователь не должен существовать");
        verify(userRepository).findByUsernameIgnoreCase(user.getUsername());


    }
    @Test
    void givenEmptyUsername_whenCheckIfUserExistsByUsername_thenReturnFalse() {
        User user = new User();
        user.setUsername("");
        when(userRepository.findByUsernameIgnoreCase(user.getUsername())).thenReturn(null);
        boolean isExist = userService.checkIfUserExistsByUsername(user.getUsername());

        assertFalse(isExist,"Пользователь не должен существовать");
        verify(userRepository).findByUsernameIgnoreCase(user.getUsername());


    }

    @Test
    void givenEmptyEmail_checkIfUserExistsByEmail_thenReturnFalse() {
        when(userRepository.findByEmailIgnoreCase("")).thenReturn(null);
        boolean isEmailExist = userService.checkIfUserExistsByEmail("");

        assertFalse(isEmailExist,"Не должно быть пользователя с пустым емейлом");
        verify(userRepository).findByEmailIgnoreCase("");
    }
    @Test
    void givenExistEmail_checkIfUserExistsByEmail_thenReturnTrue(){
        User user = new User();
        user.setEmail("test@gmail.com");
        when(userRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(user);

        boolean isUserExistByEmail = userService.checkIfUserExistsByEmail(user.getEmail());

        assertTrue(isUserExistByEmail,"Пользователь с данной почтой должен существовать");
        verify(userRepository).findByEmailIgnoreCase(user.getEmail());
    }
    @Test
    void givenNotExistEmail_checkIfUserExistsByEmail_thenReturnFalse() {
        String email = "test@gmail.com";
        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(null);
        boolean isEmailExist = userService.checkIfUserExistsByEmail(email);

        assertFalse(isEmailExist,"Не должно быть пользователя с таким емейлом");
        verify(userRepository).findByEmailIgnoreCase(email);
    }

    @Test
    void givenExistUser_getUserById_thenReturnUser(){

        User expectedUser = new User();
        expectedUser.setId(1L);
        when(userRepository.findById(1L)).thenReturn(expectedUser);

        User actualUser = userService.getUserById(1L);
        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(),actualUser.getId());
        verify(userRepository).findById(1L);

    }

    @Test
    void givenNotExistUser_getUserById_thenReturnNull(){
        when(userRepository.findById(1L)).thenReturn(null);

        User user = userService.getUserById(1L);
        assertNull(user);
        verify(userRepository).findById(1L);
    }

    @Test
    void getAllUser_shouldReturnListOfUser(){
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setActivity(true);
        user1.setEmail("user1@gmail.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setActivity(false);
        user2.setEmail("user2@gmail.com");

        List<User> expectedUsers = Arrays.asList(user1,user2);
        when(userRepository.findAllBy()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getAllUsers();

        assertNotNull(actualUsers);
        assertEquals(expectedUsers.size(),actualUsers.size());
        for (int i = 0; i < expectedUsers.size(); i++) {
            assertEquals(expectedUsers.get(i).getId(),actualUsers.get(i).getId());
            assertEquals(expectedUsers.get(i).getUsername(),actualUsers.get(i).getUsername());
            assertEquals(expectedUsers.get(i).getActivity(),actualUsers.get(i).getActivity());
            assertEquals(expectedUsers.get(i).getEmail(),actualUsers.get(i).getEmail());
        }
    }

    @Test
    void findUserByUsername_UserNotFound_ThrowException(){
        String username = "username";
        when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(null);
        assertThrows(UserNotFoundException.class,() -> userService.findUserByUsername(username));
    }
    @Test
    void findUserByUsername_UserBanned_ThrowException(){
        User user = new User();
        user.setActivity(false);
        user.setUsername("username");
        when(userRepository.findByUsernameIgnoreCase(user.getUsername())).thenReturn(user);
        assertFalse(user.getActivity(),"Юзер должен быть забанен");
        assertThrows(UserBannedException.class,() -> userService.findUserByUsername(user.getUsername()));
    }

    @Test
    void findUserByUsername_UserFoundAndActive_ReturnUser(){
        String username = "activeUser";
        User activeUser = new User();
        activeUser.setUsername(username);
        activeUser.setActivity(true);

        when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(activeUser);

        User foundUser =userService.findUserByUsername(username);

        assertNotNull(foundUser,"Юзер должен быть найден");
        assertEquals(username,foundUser.getUsername(),"Юзернеймы должны совпадать");
        assertTrue(foundUser.getActivity(),"Юзер должен быть активным");
    }

    @Test
    void givenExistNamePart_findUsersByUsernameContaining_thenReturnListOfExistUsersWithNamePart(){
        User user1 = new User();
        user1.setUsername("username");
        User user2 = new User();
        user2.setUsername("name");
        String namePart = "user";
        List<User> expectedList = List.of(user1);
        when(userRepository.findByUsernameContaining(namePart)).thenReturn(expectedList);

        List<User> actualList = userService.findUsersByUsernameContaining(namePart);

        assertNotNull(actualList);
        assertEquals(actualList.size(),1);
        assertEquals(actualList.get(0).getUsername(),user1.getUsername());
        verify(userRepository).findByUsernameContaining(namePart);



    }

    @Test
    void givenNotExistNamePart_findUsersByUsernameContaining_thenReturnListOfExistUsersWithNamePart(){
        User user1 = new User();
        user1.setUsername("username");
        User user2 = new User();
        user2.setUsername("name");
        String namePart = "aboba";
        List<User> expectedList = List.of(user1);
        when(userRepository.findByUsernameContaining(namePart)).thenReturn(null);

        List<User> actualList = userService.findUsersByUsernameContaining(namePart);

        assertNull(actualList);
        verify(userRepository).findByUsernameContaining(namePart);



    }

    @Test
     void testUpdateUsernameAndCheckExistingUsername_ShouldThrowUserAlreadyRegisteredException() {
        User alreadyRegisteredUser = new User();
        alreadyRegisteredUser.setUsername("Kolya");
        alreadyRegisteredUser.setId(1L);

        User editUser = new User();
        editUser.setId(2L);

        editUser.setUsername("Vasya");

        User existEditUser = new User();
        existEditUser.setId(2L);

        existEditUser.setUsername("Kolya");

        when(userRepository.findByUsernameIgnoreCase(existEditUser.getUsername())).thenReturn(alreadyRegisteredUser);
        when(userRepository.findById(2L)).thenReturn(editUser);


        assertThrows(UserAlreadyRegisteredException.class,() -> userService.editUser(existEditUser));

    }

    @Test
    void testUpdateEmailAndCheckExistingMail_ShouldThrowUserAlreadyRegisteredException(){
        User alreadyRegisteredUser = new User();
        alreadyRegisteredUser.setEmail("asd@gmail.com");
        alreadyRegisteredUser.setUsername("alreadyRegisteredUser");
        alreadyRegisteredUser.setId(1L);

        User editUser = new User();
        editUser.setUsername("editUser");
        editUser.setId(2L);
        editUser.setEmail("dsa@gmail.com");

        User existEditUser = new User();
        existEditUser.setId(2L);
        existEditUser.setUsername("existEditUser");

        existEditUser.setEmail("asd@gmail.com");

        when(userRepository.findByEmailIgnoreCase(existEditUser.getEmail())).thenReturn(alreadyRegisteredUser);
        when(userRepository.findById(2L)).thenReturn(editUser);


        assertThrows(UserAlreadyRegisteredException.class,() -> userService.editUser(existEditUser));

    }
    @Test
    void giveEmptyUser_ShouldReturnNullPointerException(){
        User user = new User();
        assertThrows(NullPointerException.class,() -> userService.editUser(user));
    }
    @Test
    void
}