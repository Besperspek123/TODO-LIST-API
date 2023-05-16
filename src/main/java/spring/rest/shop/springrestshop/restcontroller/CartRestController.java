package spring.rest.shop.springrestshop.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.cart.CartDTO;
import spring.rest.shop.springrestshop.dto.product.ProductDTO;
import spring.rest.shop.springrestshop.dto.product.ProductDetailsDTO;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.exception.EntityNotFoundException;
import spring.rest.shop.springrestshop.exception.UnauthorizedShopAccessException;
import spring.rest.shop.springrestshop.service.CartService;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ShopService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartRestController {

    private final ProductService productService;
    private final CartService cartService;
    private final ShopService shopService;

   @GetMapping("/carts")
    public CartDTO getCartInfo(){
       return new CartDTO(cartService.getCartForCurrentUser());
   }
   @PostMapping("/carts/products/{productId}")
    public CartDTO addProductToCart(@PathVariable long productId) throws EntityNotFoundException {
       cartService.addProductToCart(productService.getProductDetails(productId));
       return new CartDTO(cartService.getCartForCurrentUser());
   }
}
