package spring.rest.shop.springrestshop.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.product.ProductDTO;
import spring.rest.shop.springrestshop.dto.product.ProductDetailsDTO;
import spring.rest.shop.springrestshop.dto.review.ReviewCreatedDTO;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.exception.EntityNotFoundException;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ReviewService;
import spring.rest.shop.springrestshop.service.ShopService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Product",description = "The product API")

public class ProductRestController {

    private final ProductService productService;
    private final ReviewService reviewService;

    @Operation(summary = "Get all Products")
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
       List<ProductDTO> allProductList = productService.getAvailableProductsList().stream().map(ProductDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(allProductList,HttpStatus.OK);
    }


    @Operation(summary = "Create review on product")
    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<String> addReview(@PathVariable long productId, @RequestBody ReviewCreatedDTO review) throws EntityNotFoundException {
        reviewService.addNewReview(review,productId);
        return new ResponseEntity<>("Your review add",HttpStatus.OK);
    }

    @Operation(summary = "Get product details")
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDetailsDTO> getProductDetails(@PathVariable long productId) throws EntityNotFoundException {
        Product product = productService.getProductDetails(productId);

        return new ResponseEntity<>(new ProductDetailsDTO(product),HttpStatus.OK);

    }

    @Operation(summary = "Delete the product")
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable long productId){
        productService.deleteProductInShop(productId);
        return new ResponseEntity<>("product with id:" + productId +" has been deleted",HttpStatus.OK);
    }


    @Operation(summary = "Edit the product")
    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDetailsDTO> editProduct(@PathVariable long productId,@RequestBody Product product) throws EntityNotFoundException {
        productService.editProduct(productId,product);
        return new ResponseEntity<>(new ProductDetailsDTO(productService.getProductDetails(productId)),HttpStatus.OK);
    }
}
