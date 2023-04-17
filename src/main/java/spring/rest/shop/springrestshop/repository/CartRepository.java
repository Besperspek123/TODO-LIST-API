package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.rest.shop.springrestshop.entity.Cart;
import spring.rest.shop.springrestshop.entity.Characteristic;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {


}
