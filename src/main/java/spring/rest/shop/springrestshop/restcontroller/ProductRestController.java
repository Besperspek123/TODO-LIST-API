package spring.rest.shop.springrestshop.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.product.ProductDTO;
import spring.rest.shop.springrestshop.dto.product.ProductDetailsDTO;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ShopService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;
    private final ShopService shopService;

    @GetMapping("/products")
    public List<ProductDTO> getAllProduct() {
        return productService.getAvailableProductsList().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/products/{productId}")
    public ProductDetailsDTO getProductDetails(@PathVariable long productId){
        Product product = productService.getProductDetails(productId);

        return new ProductDetailsDTO(product);

    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable long productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<>("product with id:" + productId +" has been deleted",HttpStatus.ACCEPTED);
    }
    @PutMapping("/products/{productId}")
    public ResponseEntity<String> editProduct(@PathVariable long productId,@RequestBody Product product){
        productService.save(product);
        return new ResponseEntity<>("product with id "+ productId + " has been edited",HttpStatus.ACCEPTED);
    }
}
