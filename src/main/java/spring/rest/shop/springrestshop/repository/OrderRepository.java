package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.rest.shop.springrestshop.entity.Cart;
import spring.rest.shop.springrestshop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
        Order findById(long id);

}
