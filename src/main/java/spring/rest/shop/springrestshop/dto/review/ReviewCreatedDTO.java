package spring.rest.shop.springrestshop.dto.review;

import lombok.Data;
import spring.rest.shop.springrestshop.entity.Review;

@Data
public class ReviewCreatedDTO {
    private int rating;
    private String message;

}
