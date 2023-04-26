package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.rest.shop.springrestshop.entity.Cart;
import spring.rest.shop.springrestshop.entity.CartProduct;
import spring.rest.shop.springrestshop.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
        Order findById(long id);
        List<Order> findByProductList_Product_Organization_Id(int id);
}
