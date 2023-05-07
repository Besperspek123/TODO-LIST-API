package spring.rest.shop.springrestshop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.rest.shop.springrestshop.entity.Product;

@Data
public class ProductDTO {
    private long id;
    private String name;
    private long price;
    private long shopId;

    public ProductDTO (Product product){
        this.id = product.getId();
        this.name =product.getName();
        this.price = product.getPrice();
        if(product.getOrganization()!=null){
            this.shopId = product.getOrganization().getId();
        }
    }
}
