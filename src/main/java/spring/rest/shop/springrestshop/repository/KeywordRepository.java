package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.rest.shop.springrestshop.entity.Keyword;
import spring.rest.shop.springrestshop.entity.Product;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
       List<Keyword> getAllBy();

}
