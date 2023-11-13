package spring.rest.shop.springrestshop.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Cart",description = "The Cart API")

public class CartRestController {

    private final ProductService productService;
    private final CartService cartService;
    private final ShopService shopService;

    @Operation(summary = "Get all Carts")
   @GetMapping("/carts")
    public CartDTO getCartInfo(){
       return new CartDTO(cartService.getCartForCurrentUser());
   }
   @PostMapping("/carts/products/{productId}")
   @Operation(summary = "Add product to cart")
    public CartDTO addProductToCart(@PathVariable long productId) throws EntityNotFoundException {
       cartService.addProductToCart(productService.getProductDetails(productId));
       return new CartDTO(cartService.getCartForCurrentUser());
   }
}
