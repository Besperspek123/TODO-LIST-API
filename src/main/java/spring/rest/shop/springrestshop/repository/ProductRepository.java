package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.entity.User;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
       List<Product> getAllBy();
       Product getById(int id);

}
