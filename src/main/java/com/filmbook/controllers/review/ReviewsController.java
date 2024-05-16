package com.filmbook.controllers.review;

import com.filmbook.model.dto.ReviewInfoDto;
import com.filmbook.model.responses.EditReviewInfoResponse;
import com.filmbook.model.responses.ReviewInfoResponse;
import com.filmbook.services.reviewOperations.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReviewsController {
    private final ReviewService reviewService;
    @PostMapping("/addReview")
    private void addReview(@RequestBody ReviewInfoResponse reviewInfoResponse){
        reviewService.addReview(reviewInfoResponse);
    }
    @DeleteMapping("/deleteReviewById/{reviewId}")
    private void deleteReviewById(@PathVariable String reviewId){
        reviewService.deleteReviewById(Long.valueOf(reviewId));
    }
    @PatchMapping("/editReviewById/{reviewId}")
    private void editReviewById(@PathVariable String reviewId, @RequestBody EditReviewInfoResponse reviewInfoResponse){
        reviewService.editReviewById(Long.valueOf(reviewId),reviewInfoResponse);
    }
    @GetMapping("/getReviewByReviewId/{reviewId}")
    private ReviewInfoDto getReviewsByReviewId(@PathVariable String reviewId){
        return reviewService.getReviewsByReviewId(Long.valueOf(reviewId));
    }
    @GetMapping("/getReviewsByMovieId/{movieId}")
    private List<ReviewInfoDto> getReviewsByMovieId(@PathVariable String movieId){
        return reviewService.getReviewsByMovieId(Long.parseLong(movieId));
    }
    @GetMapping("/getReviewsByUserId/{userId}")
    private List<ReviewInfoDto> getReviewsByUserId(@PathVariable String userId){
        return reviewService.getReviewsByUserId(Long.valueOf(userId));
    }
//    //po to zeby dal liste recenzji filow tylko twoich znajomych
    @PostMapping("/getReviewsByMovieIdAndUsersIds/{movieId}")
    private List<ReviewInfoDto> getReviewsByMovieIdAndUsersIds(@PathVariable String movieId, @RequestBody List<String> userIds){
        return reviewService.getReviewsByMovieIdAndUsersIds(userIds, Long.valueOf(movieId));
    }
    //dla kazdego filmu (niezaleznei od filmu ale zalezenie od userIds[] tych obserwowanych to na strone glownA
    @PostMapping("/getReviewsByUsersIds")
    private List<ReviewInfoDto> getReviewsByUsersIds(@RequestBody List<String> userIds){
        return reviewService.getReviewsByUsersIds(userIds);
    }
    @GetMapping("/isUserReviewedThisMovie/{movieId}/{userId}")
    private boolean isUserReviewedThisMovie(@PathVariable String movieId,@PathVariable String userId){
        return reviewService.isUserReviewedThisMovie(movieId,userId);
    }
}
