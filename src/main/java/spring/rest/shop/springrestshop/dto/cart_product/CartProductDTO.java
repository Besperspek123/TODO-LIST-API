package spring.rest.shop.springrestshop.dto.cart_product;

import lombok.Data;
import spring.rest.shop.springrestshop.dto.product.ProductDTO;
import spring.rest.shop.springrestshop.entity.CartProduct;

@Data
public class CartProductDTO {
    private long quantity;
    private ProductDTO product;

    public CartProductDTO(CartProduct cartProduct){

        this.product = new ProductDTO(cartProduct.getProduct());
        this.quantity = cartProduct.getQuantity();
    }
}
