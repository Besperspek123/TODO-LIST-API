package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.entity.*;
import spring.rest.shop.springrestshop.repository.CartProductRepository;
import spring.rest.shop.springrestshop.repository.CartRepository;
import spring.rest.shop.springrestshop.repository.OrderRepository;
import spring.rest.shop.springrestshop.repository.UserRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final UserService userService;
    private final ProductService productService;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserRepository userRepository;

    public void createOrder(User currentUser,Cart cart){
        if(currentUser == cart.getBuyer()){
            Order order = new Order();
            order.setCustomer(currentUser);
            List<CartProduct> cartProductList = new ArrayList<>(cart.getCartProducts()) ;
            order.setProductList(cartProductList);
            order.setPrice(cart.getCostPurchase());

            for (CartProduct cartProduct:cart.getCartProducts()
                 ) {
                cartProduct.getOrdersList().add(order);
                cartProduct.setCart(null);
                cartProductRepository.save(cartProduct);
            }

            orderRepository.save(order);
            cart.setCartProducts(new ArrayList<>());
            cartService.calculateTotalCost(cart);
        }
    }

    public Order getOrderDetails(User currentUser,long orderId){

        Order order = orderRepository.findById(orderId);
        if(order.getCustomer() == currentUser || currentUser.getRoles().contains(Role.ROLE_ADMIN)){
            return order;
        }
        return new Order();
    }


}

