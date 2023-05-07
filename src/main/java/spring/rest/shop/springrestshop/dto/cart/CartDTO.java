package spring.rest.shop.springrestshop.dto.cart;

import lombok.Data;
import spring.rest.shop.springrestshop.dto.cart_product.CartProductDTO;
import spring.rest.shop.springrestshop.entity.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartDTO {
    private long id;
    private long buyerId;
    private List<CartProductDTO> ProductList;
    private long costPurchase;

    public CartDTO(Cart cart){
        this.id = cart.getId();
        this.buyerId = cart.getBuyer().getId();
        this.ProductList = cart.getCartProducts()
                .stream().map(CartProductDTO::new).collect(Collectors.toList());
        this.costPurchase = cart.getCostPurchase();

    }
}
