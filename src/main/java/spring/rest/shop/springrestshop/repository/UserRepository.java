package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.rest.shop.springrestshop.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
        User findById(long id);
        List<User> findAllBy();
        User findByEmailIgnoreCase(String email);
}
