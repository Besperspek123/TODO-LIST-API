package spring.rest.shop.springrestshop.service;

import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.dto.review.ReviewCreatedDTO;
import spring.rest.shop.springrestshop.dto.review.ReviewDTO;
import spring.rest.shop.springrestshop.entity.*;
import spring.rest.shop.springrestshop.exception.EntityNotFoundException;
import spring.rest.shop.springrestshop.repository.ReviewRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ReviewService {
    private final ProductService productService;
    private final ReviewRepository reviewRepository;
    private final UserService userService;

    public List<ReviewDTO> convertToDTOList(List<Review> reviewList){
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        return reviewList.stream().map(ReviewDTO::new).collect(Collectors.toList());
    }

    public void addNewReview(ReviewCreatedDTO review, long productId) throws EntityNotFoundException {
        User currentUser = SecurityContext.getCurrentUser();
        if(!isUserBuyThisProduct(productId)) {
            throw new AccessDeniedException("You can't add a review about a product you didn't buy");
        }
            Product product = productService.getProductDetails(productId);

            Review reviewForSave = new Review();

            reviewForSave.setRating(review.getRating());
            reviewForSave.setComment(review.getMessage());
            reviewForSave.setProduct(product);
            reviewForSave.setDateCreated(LocalDate.now());
            reviewForSave.setAuthor(currentUser);

            List<Review> listReview = product.getReviewsList();
            listReview.add(reviewForSave);

            reviewRepository.save(reviewForSave);
    }

    public void editReview(long reviewId,ReviewCreatedDTO reviewCreatedDTO) throws EntityNotFoundException {
        User currentUser = SecurityContext.getCurrentUser();
        Review review = reviewRepository.findByReviewId(reviewId);
        if(review == null){
            throw new EntityNotFoundException("Review with ID " + reviewId + " not found" );
        }
        if(currentUser != review.getAuthor()){
            throw new AccessDeniedException("You try edit review which doesn`t  belong to you");
        }

        if(reviewCreatedDTO.getMessage() != null){
            review.setComment(reviewCreatedDTO.getMessage());
        }
        if(reviewCreatedDTO.getRating() != null){
            review.setRating(reviewCreatedDTO.getRating());
        }

        reviewRepository.save(review);

    }

    //TODO need to uncomment code when be made api for review
    private boolean isUserBuyThisProduct(long productId) {
       return true;
//        User currentUser = SecurityContext.getCurrentUser();
//        List<Order> orderList = currentUser.getOrderList();
//        return orderList.stream().flatMap(order -> order.getProductList().stream())
//                .anyMatch(cartProduct -> cartProduct.getProduct().getId() == productId);
    }

    public Review getReviewFromId(long id){
        return reviewRepository.findByReviewId(id);
    }

    public void deleteReview(long reviewId) throws EntityNotFoundException {
        User currentUser = SecurityContext.getCurrentUser();
        Review review = reviewRepository.findByReviewId(reviewId);
        if(review == null){
            throw new EntityNotFoundException("Review with ID " + reviewId + " not found" );
        }
        if(currentUser != review.getAuthor()){
            throw new AccessDeniedException("You try delete review which doesn`t  belong to you");
        }

        reviewRepository.delete(review);
    }
    public List<Review> getYourReviews(){
        User currentUser =SecurityContext.getCurrentUser();
        return reviewRepository.findByAuthor(currentUser);
    }
}
