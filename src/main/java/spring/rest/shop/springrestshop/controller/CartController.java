package spring.rest.shop.springrestshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.rest.shop.springrestshop.entity.Cart;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.service.CartService;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.UserService;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @Autowired
    ProductService productService;

    @PostMapping("/addToCart")
    public String addProductToCart(@RequestParam("productId") long productId, Model model, Authentication authentication, RedirectAttributes redirectAttributes){
        User currentUser = userService.findUserByUsername(authentication.getName());
        Product productForAddToCart = productService.getProductDetails((int)productId);
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("productList",productService.getListProducts());

        if(currentUser.getCart().getProductsListInCart().contains(productForAddToCart)){
            model.addAttribute("productError","This product already have in cart");
            redirectAttributes.addAttribute("errorAddToCart", "true");
            return "redirect:/main";
        }



        cartService.addProductToCart(currentUser,productForAddToCart);

        return "redirect:/main";
    }

    @PostMapping("/deleteFromCart")
    public String deleteFromCart(@RequestParam("productId") int productId,Model model,Authentication authentication){
        User currentUser = userService.findUserByUsername(authentication.getName());
        cartService.deleteProductInCart(currentUser,productService.getProductDetails(productId));




        return "redirect:/cart";
    }


    @GetMapping("/cart")
    public String cartDetails(Model model,Authentication authentication){
        User currentUser = userService.findUserByUsername(authentication.getName());
        model.addAttribute("currentUser",currentUser);
        return "cart-details";
    }
}
