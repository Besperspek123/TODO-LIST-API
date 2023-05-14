package spring.rest.shop.springrestshop.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.review.ReviewCreatedDTO;
import spring.rest.shop.springrestshop.dto.review.ReviewDTO;
import spring.rest.shop.springrestshop.dto.review.ReviewDetailsDTO;
import spring.rest.shop.springrestshop.exception.EntityNotFoundException;
import spring.rest.shop.springrestshop.service.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewRestController {

    public final ReviewService reviewService;

    @GetMapping("/reviews/{reviewId}")
    public ReviewDetailsDTO getReviewInfo(@PathVariable long reviewId) throws EntityNotFoundException {
        if(reviewService.getReviewFromId(reviewId) == null){
            throw new EntityNotFoundException("Review with ID " + reviewId + " not found" );
        }
        return new ReviewDetailsDTO(reviewService.getReviewFromId(reviewId));
    }
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable long reviewId) throws EntityNotFoundException {

        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>("Review with ID " + reviewId + " has been deleted", HttpStatus.ACCEPTED);
    }
    @GetMapping("/reviews")
    public List<ReviewDTO> getAllYourReviews(){
        return reviewService.getYourReviews().stream().map(ReviewDTO::new).collect(Collectors.toList());
    }

    @PutMapping("reviews/{reviewId}")
    public ReviewDetailsDTO editReview(@PathVariable long reviewId ,@RequestBody ReviewCreatedDTO review ) throws EntityNotFoundException {
        reviewService.editReview(reviewId,review);
        return new ReviewDetailsDTO(reviewService.getReviewFromId(reviewId));
    }
}
