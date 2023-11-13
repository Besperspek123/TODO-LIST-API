package spring.rest.shop.springrestshop.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Review", description = "The review API")
public class ReviewRestController {

    public final ReviewService reviewService;

    @Operation(summary = "Get review info")
    @GetMapping("/reviews/{reviewId}")
    public ReviewDetailsDTO getReviewInfo(@PathVariable long reviewId) throws EntityNotFoundException {
        if (reviewService.getReviewFromId(reviewId) == null) {
            throw new EntityNotFoundException("Review with ID " + reviewId + " not found");
        }
        return new ReviewDetailsDTO(reviewService.getReviewFromId(reviewId));
    }

    @Operation(summary = "Delete the review")
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable long reviewId) throws EntityNotFoundException {

        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>("Review with ID " + reviewId + " has been deleted", HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Get all Review for current user")
    @GetMapping("/reviews")
    public List<ReviewDTO> getAllYourReviews() {
        return reviewService.getYourReviews().stream().map(ReviewDTO::new).collect(Collectors.toList());
    }

    @Operation(summary = "Edit the review")
    @PutMapping("reviews/{reviewId}")
    public ReviewDetailsDTO editReview(@PathVariable long reviewId, @RequestBody ReviewCreatedDTO review) throws EntityNotFoundException {
        reviewService.editReview(reviewId, review);
        return new ReviewDetailsDTO(reviewService.getReviewFromId(reviewId));
    }
}
