package spring.rest.shop.springrestshop.dto.review;

import lombok.Data;
import lombok.NoArgsConstructor;
import spring.rest.shop.springrestshop.entity.Review;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReviewDTO {
    private long id;
    private long ownerId;
    private int rating;
    private String message;
    private LocalDate date;

    public ReviewDTO (Review review){
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getReviewId());
        reviewDTO.setOwnerId(review.getAuthor().getId());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setMessage(review.getComment());
        reviewDTO.setDate(review.getDateCreated());
    }

}
