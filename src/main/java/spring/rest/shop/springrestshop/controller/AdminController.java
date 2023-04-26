package spring.rest.shop.springrestshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.entity.Order;
import spring.rest.shop.springrestshop.entity.Organization;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.service.ShopService;
import spring.rest.shop.springrestshop.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ShopService shopService;

    @GetMapping("/panel")
    public String userList(Model model, Authentication authentication) {
        model.addAttribute("currentUser",userService.findUserByUsername(authentication.getName()));
        model.addAttribute("allUsers", userService.getAllUsers());
        return "redirect:/admin/users";
    }

    @GetMapping("/users")
    public String getUsersInAdminPanel(Model model, Authentication authentication){
        User currentUser = userService.findUserByUsername(authentication.getName());
        model.addAttribute("currentUser",currentUser);
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers",allUsers);
        return "admin/users";
    }
    @GetMapping("/shops")
    public String getShopsInAdminPanel(Model model, Authentication authentication){
        User currentUser = userService.findUserByUsername(authentication.getName());
        model.addAttribute("currentUser",currentUser);
        List<Organization> allShops = shopService.getAllShops();
        model.addAttribute("allShops",allShops);
        return "admin/shops";
    }
    @GetMapping("/searchUser")
    public String searchUser(@RequestParam(name = "searchQuery") String searchQuery,Model model,Authentication authentication){
        User currentUser = userService.findUserByUsername(authentication.getName());
        model.addAttribute("currentUser",currentUser);
        List<User> users = userService.findUsersByUsernameContaining(searchQuery);
        model.addAttribute("allUsers",users);
        return "admin/users";
    }
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam(name = "userId")long userId,Model model,Authentication authentication){
        User currentUser = userService.findUserByUsername(authentication.getName());
        model.addAttribute("currentUser",currentUser);
        User user = userService.getUserById(userId);
        model.addAttribute("user",user);
        return "admin/user-info";
    }
    @GetMapping("/shopInfo")
    public String shopInfo(@RequestParam(name = "shopId")long shopId,Model model,Authentication authentication){
        User currentUser = userService.findUserByUsername(authentication.getName());
        model.addAttribute("currentUser",currentUser);
        Organization shop = shopService.getShopDetails((int)shopId);
        model.addAttribute("currentShop",shop);
        return "shop/details";
    }
    @GetMapping("/searchShop")
    public String searchShop(@RequestParam(name = "searchQuery") String searchQuery,Model model,Authentication authentication){
        User currentUser = userService.findUserByUsername(authentication.getName());
        model.addAttribute("currentUser",currentUser);
        List<Organization> shops = shopService.getShopsByNameContaining(searchQuery);
        model.addAttribute("allShops",shops);
        return "admin/shops";
    }
    @PostMapping("/banUser")
    public String banUser(@RequestParam(name = "userId")long userId,Authentication authentication){
        User userForBan = userService.getUserById(userId);
        User currentUser = userService.findUserByUsername(authentication.getName());
        userService.banUser(userForBan,currentUser);
        return "redirect:/admin/userInfo?userId=" + userId;
    }
    @PostMapping("/unbanUser")
    public String unbanUser(@RequestParam(name = "userId")long userId,Authentication authentication){
        User userForUnban = userService.getUserById(userId);
        User currentUser = userService.findUserByUsername(authentication.getName());
        userService.unbanUser(userForUnban,currentUser);
        return "redirect:/admin/userInfo?userId=" + userId;
    }

    @GetMapping("/editUser")
    public String editUser(@RequestParam(name = "userId") long userId,Model model,Authentication authentication){
        User userForm = userService.getUserById(userId);
        model.addAttribute("userForm",userForm);
        return "admin/edit-user";
    }

    @GetMapping("/addBalance")
    public String addBalance(@RequestParam(name = "userId") long userId,Model model){
        User user = userService.getUserById(userId);
        model.addAttribute("user",user);
        return "admin/add-balance";
    }
    @PostMapping("/addBalance")
    public String addBalance(@RequestParam(name = "deposit")int deposit,@RequestParam(name = "userId")long userId){
        User user = userService.getUserById(userId);
        userService.addBalance(user,deposit);
        return "redirect:/admin/userInfo?userId=" + userId;
    }

    @GetMapping("/checkOrders")
    public String checkUserOrders(@RequestParam(name = "userId") long userId, Authentication authentication, Model model){
        User currentUser = userService.findUserByUsername(authentication.getName());
        model.addAttribute("currentUser",currentUser);
        List<Order> orderList = userService.getUserById(userId).getOrderList();
        model.addAttribute("ordersList",orderList);

        return "order/orders-page";
    }
    @GetMapping("/moderation")
    public String moderationShopPage(Model model, Authentication authentication){
        User currentUser = userService.findUserByUsername(authentication.getName());
        model.addAttribute("currentUser",currentUser);
        List<Organization> allShops = shopService.getAllModerationShops();
        model.addAttribute("allShops",allShops);
        return "admin/moderation";
    }
    @GetMapping("/searchModerationShop")
    public String searchModerationShop(@RequestParam(name = "searchQuery") String searchQuery,Model model,Authentication authentication){
        User currentUser = userService.findUserByUsername(authentication.getName());
        model.addAttribute("currentUser",currentUser);
        List<Organization> shops = shopService.getAllModerationShopsByNameContaining(searchQuery);
        model.addAttribute("allShops",shops);
        return "admin/moderation";
    }
    @PostMapping("/approveShop")
    public String approveShop(@RequestParam(name = "shopId") long shopId){
        shopService.approveShop(shopId);

        return "redirect:/admin/moderation";
    }

    @PostMapping("/disapproveAllShops")
    public String disapproveAllShops(Authentication authentication){
        shopService.disapproveAllShops(userService.findUserByUsername(authentication.getName()));
        return "redirect:/admin/moderation";
    }

    @PostMapping("/approveAllShops")
    public String approveAllShops(){
        shopService.approveAllShops();
        return "redirect:/admin/moderation";
    }
    @PostMapping("/deleteShop")
    public String deleteShop(@RequestParam(name = "shopId")long shopId,Authentication authentication){
        User currentUser = userService.findUserByUsername(authentication.getName());
        shopService.deleteShop((int)shopId,currentUser);
        return "redirect:/admin/shops";
    }

    @GetMapping("/viewShopsUser")
    public String viewShopsUser(@RequestParam(name = "userId") long userId, Authentication authentication, Model model){
        List<Organization> userShopList = shopService.getListActivityShopForCurrentUser(userService.getUserById(userId));
        model.addAttribute("allShops",userShopList);
                User currentUser = userService.findUserByUsername(authentication.getName());
        model.addAttribute("currentUser",currentUser);
        return "admin/shops";
    }
}
