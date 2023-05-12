package spring.rest.shop.springrestshop.service;

import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.aspect.CurrentUserAspect;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.dto.review.ReviewCreatedDTO;
import spring.rest.shop.springrestshop.dto.review.ReviewDTO;
import spring.rest.shop.springrestshop.entity.*;
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

    public void addReview(ReviewCreatedDTO review, long productId){
        User currentUser = SecurityContext.getCurrentUser();
        if(isUserBuyThisProduct(productId)){
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
        else throw new AccessDeniedException("You can't add a review about a product you didn't buy");
    }

    private boolean isUserBuyThisProduct(long productId) {
        User currentUser = SecurityContext.getCurrentUser();
        List<Order> orderList = currentUser.getOrderList();
        return orderList.stream().flatMap(order -> order.getProductList().stream())
                .anyMatch(cartProduct -> cartProduct.getProduct().getId() == productId);
    }
}
