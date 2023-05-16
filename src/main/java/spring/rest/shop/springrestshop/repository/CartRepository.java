package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.rest.shop.springrestshop.entity.Cart;
import spring.rest.shop.springrestshop.entity.Characteristic;
import spring.rest.shop.springrestshop.entity.User;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByCartProducts_Product_Organization_Id(long id);

    List<Cart> getAllBy();

    Cart findByBuyer(User user);
}