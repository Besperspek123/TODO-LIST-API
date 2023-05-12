package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.rest.shop.springrestshop.entity.Organization;
import spring.rest.shop.springrestshop.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
        public List<Product> getAllBy();
        public Product getById(long id);
        public List<Product> findAllByName(String name);
        public List<Product> findAllByNameContaining(String name);

        public List<Product> findByOrganization_Id(long id);

        public List<Product> findAllByOrganization_ActivityTrue();


}
