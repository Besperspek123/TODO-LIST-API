package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.rest.shop.springrestshop.entity.Product;
import spring.rest.shop.springrestshop.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
        User findByUsername(String username);
        User findById(long id);
        List<User> findAllBy();
}
