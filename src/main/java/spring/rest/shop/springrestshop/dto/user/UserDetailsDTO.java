package spring.rest.shop.springrestshop.dto.user;

import lombok.Data;
import spring.rest.shop.springrestshop.dto.cart.CartDTO;
import spring.rest.shop.springrestshop.dto.order.OrderDTO;
import spring.rest.shop.springrestshop.dto.shop.ShopDTO;
import spring.rest.shop.springrestshop.entity.Role;
import spring.rest.shop.springrestshop.entity.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDetailsDTO {
    private long id;
    private String name;
    private String email;
    private long Balance;

    private List<ShopDTO> shopList;

    private Boolean activity;
    private CartDTO cart;
    private Set<Role> roles;
    private List<OrderDTO> orderList;


    public UserDetailsDTO(User user){
        this.id = user.getId();
        this.name = user.getUsername();
        this.email = user.getEmail();
        this.Balance = user.getBalance();
        this.shopList = user.getOrganizationList()
                .stream().map(ShopDTO::new).collect(Collectors.toList());
        this.activity = user.getActivity();
        this.cart = new CartDTO(user.getCart());
        this.roles = user.getRoles();

    }

}
