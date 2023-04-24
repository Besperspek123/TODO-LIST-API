package spring.rest.shop.springrestshop.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.entity.Organization;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ShopService;
import spring.rest.shop.springrestshop.service.UserService;

import java.util.List;

@Controller
@Slf4j
public class ShopController {
    @Autowired
    UserService userService;
    @Autowired
    ShopService shopService;
    @Autowired
    ProductService productService;


            @GetMapping("/shop")
            public String pageMyShop(Model model,Authentication authentication){
                User currentUser = userService.findUserByUsername(authentication.getName());
                model.addAttribute("currentUser",currentUser);
                List<Organization> shopListForCurrentUser = shopService.
                        getListActivityShopForCurrentUser(userService.findUserByUsername(authentication.getName()));

                model.addAttribute("listShopForCurrentUser",shopListForCurrentUser);

                return "shop";
            }

        @GetMapping("/addShop")
        public String addShopPage(Model model,Authentication authentication){
        User currentUser = userService.findUserByUsername(authentication.getName());
            model.addAttribute("currentUser",currentUser);
            model.addAttribute("shopForm",new Organization());
            return "addShop";
        }

        @PostMapping("/addShop")
    public String addShop(@ModelAttribute("shopForm") @Validated Organization shopForm, BindingResult bindingResult,
                          Model model,Authentication authentication){
            if (bindingResult.hasErrors()) {
                return "addShop";
            }
            User currentUser = userService.findUserByUsername(authentication.getName());
            shopService.saveShop(shopForm,currentUser);

            return "redirect:/shop";
        }

        @GetMapping("/editShop")
        public String editShop (@RequestParam("shopId") int shopId, Model model,Authentication authentication){
                User currentUser = userService.findUserByUsername(authentication.getName());
            model.addAttribute("currentUser",currentUser);
            if(shopService.getShopDetails(shopId).getOwner() == currentUser
                    || currentUser.getRoles().stream().anyMatch(role -> role.name().equals("ROLE_ADMIN"))){
                Organization currentShop = shopService.getShopDetails(shopId);
                        model.addAttribute("shopForm",currentShop);
            }

                return "addShop";
        }
        @PostMapping("/deleteShop")
        public String deleteShop(@RequestParam("shopId") int shopId,Model model,Authentication authentication)
        {
            User currentUser = userService.findUserByUsername(authentication.getName());
            if(shopService.getShopDetails(shopId).getOwner() == currentUser
                    || currentUser.getRoles().stream().anyMatch(role -> role.name().equals("ROLE_ADMIN"))){
                shopService.deleteShop(shopId,currentUser);
            }

            return"redirect:/shop" ;
        }

        @GetMapping("/viewShop")
        public String viewShop(@RequestParam("shopId") int id,Model model,Authentication authentication){
                User currentUser = userService.findUserByUsername(authentication.getName());
            model.addAttribute("currentUser",currentUser);
            model.addAttribute("currentShop",shopService.getShopDetails(id));
            System.out.println(currentUser.getAuthorities().toString());
            return "shop-details";

        }
}
