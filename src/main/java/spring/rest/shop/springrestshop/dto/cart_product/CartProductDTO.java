package spring.rest.shop.springrestshop.dto.cart_product;

import lombok.Data;
import spring.rest.shop.springrestshop.dto.product.ProductDTO;
import spring.rest.shop.springrestshop.entity.CartProduct;

@Data
public class CartProductDTO {
    private long id;
    private ProductDTO productDTO;
    private long quantity;

    public CartProductDTO(CartProduct cartProduct){

        this.id = cartProduct.getId();
        this.productDTO = new ProductDTO(cartProduct.getProduct());
        this.quantity = cartProduct.getQuantity();
    }
}
