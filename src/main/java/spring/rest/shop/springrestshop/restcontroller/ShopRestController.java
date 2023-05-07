package spring.rest.shop.springrestshop.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.exception.UnauthorizedShopAccessException;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ShopService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShopRestController {

    private final ProductService productService;
    private final ShopService shopService;

    @PostMapping("/shop/{shopId}/products/")
    public ResponseEntity<String> addProductToShop(@PathVariable long shopId,  @RequestBody Product product) throws UnauthorizedShopAccessException {
        productService.addProduct(product,shopId);
        return new ResponseEntity<>("Product " + product.getName() + "successfully add to shop with id: " + shopId,
                HttpStatus.ACCEPTED);
    }
}
