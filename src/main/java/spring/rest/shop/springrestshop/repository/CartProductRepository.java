package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.rest.shop.springrestshop.entity.Cart;
import spring.rest.shop.springrestshop.entity.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {


}
