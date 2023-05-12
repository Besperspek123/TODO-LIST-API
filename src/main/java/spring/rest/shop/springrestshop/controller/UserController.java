package spring.rest.shop.springrestshop.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ShopService;
import spring.rest.shop.springrestshop.service.UserService;

@Controller
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ShopService shopService;
    @Autowired
    ProductService productService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(){
        return "redirect:/main";
    }
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }
    @GetMapping("/main")
    public String pageAfterSuccessLogin(Model model){
        model.addAttribute("productList", productService.getAvailableProductsList());
        return "main";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Validated User userForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {



        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (userService.checkIfUserExistsByUsername(userForm.getUsername())){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        if (userService.checkIfUserExistsByEmail(userForm.getEmail())){
            model.addAttribute("emailError", "Пользователь с такой почтой уже существует");
            return "registration";
        }

        userService.saveUser(userForm);


        redirectAttributes.addAttribute("success", "true");
        return "redirect:/login";
    }

    @GetMapping("/users")
    public ResponseEntity<User> userInfo(){
        return null;
    }
}
