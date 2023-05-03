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
import spring.rest.shop.springrestshop.service.CartProductService;
import spring.rest.shop.springrestshop.service.CartService;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.UserService;

@Controller
public class CartController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartProductService cartProductService;

    @PostMapping("/addToCart")
    public String addProductToCart(@RequestParam("productId") int productId, Model model, Authentication authentication, RedirectAttributes redirectAttributes){
        User currentUser = userService.findUserByUsername(authentication.getName());
        Product productForAddToCart = productService.getProductDetails(productId);


        if (productForAddToCart.getAmountInStore() == 0){
            redirectAttributes.addAttribute("cartProductError","В магазине нет товара");
            return "redirect:/main";
        }
        if(!cartProductService.checkAvailability(currentUser,productForAddToCart)){
            redirectAttributes.addAttribute("cartProductError","Вы не можете добавить в корзину такое количество" +
                    "товара, которого нет в магазине");
                    return "redirect:/main";
        }

        cartProductService.saveCartProduct(currentUser,productForAddToCart);





        return "redirect:/main";
    }

    @PostMapping("/deleteFromCart")
    public String deleteFromCart(@RequestParam("productId") int productId,Model model,Authentication authentication){
        User currentUser = userService.findUserByUsername(authentication.getName());
        cartService.deleteProductInCart(currentUser, productId);




        return "redirect:/cart";
    }


    @GetMapping("/cart")
    public String cartDetails(Model model,Authentication authentication){
        User currentUser = userService.findUserByUsername(authentication.getName());
        model.addAttribute("currentUser",currentUser);
        return "cart/details";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam(name = "productId") Integer productId,@RequestParam(name = "quantity") int quantity,Authentication authentication) {
        Cart cart = userService.findUserByUsername(authentication.getName()).getCart();
        cartService.updateCartItem(cart, productId, quantity);
        return "redirect:/cart";
    }
}
