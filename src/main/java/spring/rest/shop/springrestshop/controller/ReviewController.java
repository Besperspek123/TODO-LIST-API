package spring.rest.shop.springrestshop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.dto.review.ReviewCreatedDTO;
import spring.rest.shop.springrestshop.entity.Organization;
import spring.rest.shop.springrestshop.entity.Review;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.EntityNotFoundException;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ReviewService;
import spring.rest.shop.springrestshop.service.ShopService;
import spring.rest.shop.springrestshop.service.UserService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

   private final ReviewService reviewService;


    @GetMapping("/addReview")
    public String addReview(@RequestParam(name = "productId") long productId, Model model){
        model.addAttribute("productId",productId);
        model.addAttribute("reviewForm",new ReviewCreatedDTO());
        return "review/add-review";
    }

    @PostMapping("/addReview")
    public String addReview(@RequestParam(name = "productId") long productId, @Validated ReviewCreatedDTO reviewForm) throws EntityNotFoundException {
        reviewService.addNewReview(reviewForm,productId);
        return "redirect:/viewProduct?productId=" + productId;
    }

}
