package spring.rest.shop.springrestshop.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.rest.shop.springrestshop.dto.product.ProductDTO;
import spring.rest.shop.springrestshop.entity.Organization;

import java.util.List;
import java.util.stream.Collectors;

@Data

public class ShopDTO {
    private long id;
    private String name;
    private String description;

    private List<ProductDTO> products;

    public ShopDTO (Organization shop){
        this.id = shop.getId();
        this.name = shop.getName();
        this.description = shop.getDescription();
        this.products = shop.getProductList().stream().map(ProductDTO::new).collect(Collectors.toList());

    }


}
