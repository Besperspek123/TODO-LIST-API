package spring.rest.shop.springrestshop.dto.order;

import lombok.Data;
import spring.rest.shop.springrestshop.dto.cart_product.CartProductDTO;
import spring.rest.shop.springrestshop.dto.product.ProductDTO;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.entity.Order;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDTO {
    private long id;
    private UserDTO user;
    private long price;
    private List<CartProductDTO> productList;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.user = new UserDTO(order.getCustomer());
        this.price = order.getPrice();
        this.productList = order.getProductList().stream().map(CartProductDTO::new).collect(Collectors.toList());
    }
}
