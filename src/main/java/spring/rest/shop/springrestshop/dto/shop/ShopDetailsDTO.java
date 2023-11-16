package spring.rest.shop.springrestshop.dto.shop;

import lombok.Data;
import spring.rest.shop.springrestshop.entity.Organization;
import spring.rest.shop.springrestshop.entity.Product;

import java.util.List;

@Data
public class ShopDetailsDTO {
    private long id;
    private String name;
    private String description;
    private List<Product> productsList;

    public ShopDetailsDTO (Organization shop){
        this.id = shop.getId();
        this.name = shop.getName();
        this.description = shop.getDescription();
        this.productsList = shop.getProductList();
    }
}
