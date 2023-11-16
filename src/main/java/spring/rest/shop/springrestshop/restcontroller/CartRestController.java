package spring.rest.shop.springrestshop.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.cart.CartDTO;
import spring.rest.shop.springrestshop.exception.EntityNotFoundException;
import spring.rest.shop.springrestshop.service.CartService;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ShopService;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Cart",description = "The Cart API")

public class CartRestController {

    private final ProductService productService;
    private final CartService cartService;

    @Operation(summary = "Get cart for current User")
   @GetMapping("/carts")
    public ResponseEntity<CartDTO> getCartInfoForCurrentUser(){
        return new ResponseEntity<>(new CartDTO(cartService.getCartForCurrentUser()), HttpStatus.OK);
   }
   @PostMapping("/carts/products/{productId}")
   @Operation(summary = "Add product to cart")
    public ResponseEntity<CartDTO> addProductToCartForCurrentUser(@PathVariable long productId) throws EntityNotFoundException {
       cartService.addProductToCart(productService.getProductDetails(productId));
       return new ResponseEntity<>(new CartDTO(cartService.getCartForCurrentUser()), HttpStatus.OK);
   }
}
