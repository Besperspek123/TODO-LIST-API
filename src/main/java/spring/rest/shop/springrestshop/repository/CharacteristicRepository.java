package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.rest.shop.springrestshop.entity.Characteristic;
import spring.rest.shop.springrestshop.entity.Keyword;

import java.util.List;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {
       List<Characteristic> getAllBy();

}
