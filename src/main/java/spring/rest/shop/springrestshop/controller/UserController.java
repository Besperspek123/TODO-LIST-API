package spring.rest.shop.springrestshop.controller;


import  lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
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

    @RequestMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/documentation")
    public String redirectSwagger() {
        return "redirect:/swagger-ui/index.html#/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/main";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @GetMapping("/main")
    public String pageAfterSuccessLogin(Model model) {
        model.addAttribute("productList", productService.getAvailableProductsList());
        return "main";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Validated User userForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {


        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            if (userForm.getId() == null) {
                return "registration";
            } else {
                model.addAttribute("userForm", userForm);
                return "admin/edit-user";
            }
        }
        if (userService.checkIfUserExistsByUsername(userForm)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            if (userForm.getId() == null) {
                return "registration";
            } else {
                model.addAttribute("userForm", userForm);
                return "admin/edit-user";
            }
        }
        if (userService.checkIfUserExistsByEmail(userForm)) {
            model.addAttribute("emailError", "Пользователь с такой почтой уже существует");
            if (userForm.getId() == null) {
                return "registration";
            } else {
                model.addAttribute("userForm", userForm);
                return "admin/edit-user";
            }
        }
        boolean isNewUser = userForm.getId() == null;
        if (isNewUser) {
            userService.saveNewUser(userForm);
            redirectAttributes.addAttribute("success", "true");
            return "redirect:/login";
        } else {
            userService.editUser(userForm);
            redirectAttributes.addAttribute("success", "true");
            return "redirect:/admin/users";
        }
    }

    @GetMapping("/users")
    public ResponseEntity<User> userInfo() {
        return null;
    }
}
