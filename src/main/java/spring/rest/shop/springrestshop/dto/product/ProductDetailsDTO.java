package spring.rest.shop.springrestshop.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import spring.rest.shop.springrestshop.dto.review.ReviewDTO;
import spring.rest.shop.springrestshop.dto.shop.ShopDTO;
import spring.rest.shop.springrestshop.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductDetailsDTO {

    private long id;
    private String name;
    private String description;
    private ShopDTO shop;
    private long price;
    private long amount;
    private long sale;
    private List<ReviewDTO> review;
    private String keywords;
    private String characteristics;

    public ProductDetailsDTO (Product product){
        this.id = product.getId();
        this.name = product.getName();
        if (product.getOrganization()!= null){
            this.shop = new ShopDTO(product.getOrganization());
        }
        this.price = product.getPrice();
        this.amount = product.getAmountInStore();
        this.sale = product.getSale();
        this.description = product.getDescription();
        this.review = product.getReviewsList().stream().map(ReviewDTO::new).collect(Collectors.toList());
        this.keywords = product.getKeywordsString();
        this.characteristics = product.getCharacteristicsString();
    }
}
