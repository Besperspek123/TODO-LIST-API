package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.rest.shop.springrestshop.entity.Organization;
import spring.rest.shop.springrestshop.entity.User;

import java.util.List;

public interface ShopRepository extends JpaRepository<Organization, Long> {
        List<Organization> getAllByOwnerAndActivityTrue(User user);
        List<Organization> getAllByActivityIsFalse();
        Organization getOrganizationById(int id);

        List<Organization> getAllBy();
        List<Organization> getAllByNameContaining(String string);
}
