package spring.rest.shop.springrestshop.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.rest.shop.springrestshop.entity.Organization;

import java.util.List;

@Data

public class ShopDTO {
    private long id;
    private String name;
    private String description;

    public ShopDTO (Organization shop){
        this.id = shop.getId();
        this.name = shop.getName();
        this.description = shop.getDescription();

    }


}
