package spring.rest.shop.springrestshop.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.product.ProductDTO;
import spring.rest.shop.springrestshop.dto.product.ProductDetailsDTO;
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

    @PostMapping("/shop/{shopId}/products")
    public ProductDetailsDTO addProductToShop(@PathVariable long shopId, @RequestBody Product product) throws UnauthorizedShopAccessException {
        productService.addProduct(product,shopId);
        return new ProductDetailsDTO(product);
    }
    @GetMapping("/shop/{shopId}/products")
    public List<ProductDTO> getProductsForYourShop(@PathVariable long shopId) throws EntityNotFoundException {
        return productService.getProductsFromShop(shopId).stream().map(ProductDTO::new).collect(Collectors.toList());
    }
}
