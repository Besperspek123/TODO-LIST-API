package spring.rest.shop.springrestshop.dto.review;

import lombok.Data;
import lombok.NoArgsConstructor;
import spring.rest.shop.springrestshop.entity.Review;

import java.time.LocalDate;

@Data
public class ReviewDTO {
    private long id;
    private long ownerId;
    private int rating;
    private String message;
    private LocalDate date;

    public ReviewDTO (Review review){
        this.id = review.getReviewId();
        this.ownerId = review.getAuthor().getId();
        this.rating = review.getRating();
        this.message = review.getComment();
        this.date = review.getDateCreated();
    }

}
