package spring.rest.shop.springrestshop.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.product.ProductDTO;
import spring.rest.shop.springrestshop.dto.product.ProductDetailsDTO;
import spring.rest.shop.springrestshop.dto.shop.ShopDTO;
import spring.rest.shop.springrestshop.entity.Organization;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.exception.EntityNotFoundException;
import spring.rest.shop.springrestshop.exception.UnauthorizedShopAccessException;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ShopService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShopRestController {

    private final ProductService productService;
    private final ShopService shopService;

    @PostMapping("/shop/{shopId}")
    public ProductDetailsDTO addProductToShop(@PathVariable long shopId, @RequestBody Product product) throws UnauthorizedShopAccessException, EntityNotFoundException {
        productService.addProduct(product,shopId);
        return new ProductDetailsDTO(product);
    }
    @GetMapping("/shop/{shopId}")
    public ShopDTO getProductsForYourShop(@PathVariable long shopId) throws EntityNotFoundException {
        return new ShopDTO(shopService.getShopDetails(shopId));
    }
    @DeleteMapping("/shop/{shopId}")
    public ResponseEntity<String> deleteShop(@PathVariable long shopId) throws EntityNotFoundException {
        shopService.deleteShop(shopId);
        return new ResponseEntity<>("Shop with ID: " + shopId +" has been deleted",HttpStatus.ACCEPTED);

    }
    @PutMapping("/shop/{shopId}")
    public ShopDTO editShop(@PathVariable long shopId, @RequestBody Organization shop) throws EntityNotFoundException {
        shopService.editShop(shopId,shop);
        return new ShopDTO(shopService.getShopById(shopId));
    }
}
