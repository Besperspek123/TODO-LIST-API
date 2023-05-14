package spring.rest.shop.springrestshop.dto.review;

import lombok.Data;
import spring.rest.shop.springrestshop.dto.product.ProductDTO;
import spring.rest.shop.springrestshop.entity.Review;

import java.time.LocalDate;

@Data
public class ReviewDetailsDTO {
    private long id;
    private long ownerId;
    private int rating;
    private String message;
    private LocalDate date;
    private ProductDTO product;

    public ReviewDetailsDTO(Review review){
        this.id = review.getReviewId();
        this.ownerId = review.getAuthor().getId();
        this.rating = review.getRating();
        this.message = review.getComment();
        this.date = review.getDateCreated();
        this.product = new ProductDTO(review.getProduct());
    }

}
