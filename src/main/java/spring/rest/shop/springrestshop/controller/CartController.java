package spring.rest.shop.springrestshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.rest.shop.springrestshop.entity.Cart;
import spring.rest.shop.springrestshop.entity.CartProduct;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.EntityNotFoundException;
import spring.rest.shop.springrestshop.service.CartProductService;
import spring.rest.shop.springrestshop.service.CartService;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.UserService;

import java.util.HashMap;
import java.util.Map;

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
    public String addProductToCart(@RequestParam("productId") int productId, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        Product productForAddToCart = productService.getProductDetails(productId);

        if (productForAddToCart.getAmountInStore() == 0){
            redirectAttributes.addAttribute("cartProductError","Shop doesn`t have enough amount of products");
            return "redirect:/main";
        }
        if(!cartService.checkAvailability(productForAddToCart)){
            redirectAttributes.addAttribute("cartProductError","Shop doesn`t have enough amount of products");
                    return "redirect:/main";
        }

        cartService.addProductToCart(productForAddToCart);

        return "redirect:/main";
    }

    @PostMapping("/deleteFromCart")
    public String deleteFromCart(@RequestParam("productId") int productId){
        cartService.deleteProductInCart(productId);
        return "redirect:/cart";
    }


    @GetMapping("/cart")
    public String cartDetails(Model model){
        return "cart/details";
    }

    @PostMapping("/cart/update")
    @ResponseBody
    public Map<String, Object> updateCart(@RequestParam(name = "productId") long cartProductID, @RequestParam(name = "quantity") int quantity, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        CartProduct cartProduct = cartProductService.getById(cartProductID);

        if (cartProduct.getProduct().getAmountInStore() == 0) {
            response.put("success", false);
            response.put("message", "Shop doesn't have enough amount of products");
        } else if (!cartService.checkAvailability(cartProduct.getProduct())) {
            response.put("success", false);
            response.put("message", "Shop doesn't have enough amount of products");
        } else if(quantity <= 0){
            System.out.println(quantity);
            response.put("success", false);
            response.put("message", "Quantity cant be 0");
        }
        else {
            cartService.updateCartItem(cartProductID, quantity);
            response.put("success", true);
            response.put("message", "Cart updated successfully");
        }

        return response;
    }
}
