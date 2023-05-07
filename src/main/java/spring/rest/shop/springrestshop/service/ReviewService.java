package spring.rest.shop.springrestshop.service;

import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.dto.review.ReviewDTO;
import spring.rest.shop.springrestshop.entity.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReviewService {
    public List<ReviewDTO> convertToDTOList(List<Review> reviewList){
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        return reviewList.stream().map(ReviewDTO::new).collect(Collectors.toList());
    }
}
