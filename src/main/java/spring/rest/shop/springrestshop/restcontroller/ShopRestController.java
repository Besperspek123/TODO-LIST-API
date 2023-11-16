package spring.rest.shop.springrestshop.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.product.ProductDetailsDTO;
import spring.rest.shop.springrestshop.dto.shop.ShopDTO;
import spring.rest.shop.springrestshop.dto.shop.ShopDetailsDTO;
import spring.rest.shop.springrestshop.dto.shop.ShopEditDTO;
import spring.rest.shop.springrestshop.entity.Organization;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.exception.EntityNotFoundException;
import spring.rest.shop.springrestshop.exception.UnauthorizedShopAccessException;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ShopService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Shop",description = "The shop API")
public class ShopRestController {

    private final ProductService productService;
    private final ShopService shopService;

    @Operation(summary = "Add product to the shop")
    @PostMapping("/shop/{shopId}")
    public ResponseEntity<ProductDetailsDTO> addProductToShop(@PathVariable long shopId, @RequestBody Product product) throws UnauthorizedShopAccessException, EntityNotFoundException {
        productService.addProduct(product,shopId);
        return new ResponseEntity<>(new ProductDetailsDTO(product),HttpStatus.OK);
    }
    @Operation(summary = "Get info about any shop")
    @GetMapping("/shop/{shopId}")
    public ResponseEntity<ShopDetailsDTO> getInfoAboutShop(@PathVariable long shopId) throws EntityNotFoundException {
        return new ResponseEntity<>(new ShopDetailsDTO(shopService.getShopDetails(shopId)),HttpStatus.OK);
    }
    @Operation(summary = "Delete the shop")
    @DeleteMapping("/shop/{shopId}")
    public ResponseEntity<String> deleteShop(@PathVariable long shopId) throws EntityNotFoundException {
        shopService.deleteShop(shopId);
        return new ResponseEntity<>("Shop with ID: " + shopId +" has been deleted",HttpStatus.OK);

    }
    @Operation(summary = "Edit the shop")
    @PutMapping("/shop/{shopId}")
    public ResponseEntity<ShopDTO> editShop(@PathVariable long shopId, @RequestBody ShopEditDTO shop) throws EntityNotFoundException {
        shopService.editShop(shopId,shop);
        return new ResponseEntity<>(new ShopDTO(shopService.getShopById(shopId)),HttpStatus.OK);
    }
}
