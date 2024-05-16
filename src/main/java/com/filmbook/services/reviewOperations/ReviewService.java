package com.filmbook.services.reviewOperations;

import com.filmbook.model.database.MovieInfo;
import com.filmbook.model.database.ReviewInfo;
import com.filmbook.model.database.User;
import com.filmbook.model.dto.MovieInfoDto;
import com.filmbook.model.dto.ReviewInfoDto;
import com.filmbook.model.requestsToSend.UserInfo;
import com.filmbook.model.responses.EditReviewInfoResponse;
import com.filmbook.model.responses.ReviewInfoResponse;
import com.filmbook.repositories.MovieInfoRepository;
import com.filmbook.repositories.ReviewInfoRepository;
import com.filmbook.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewInfoRepository reviewInfoRepository;
    private final UserRepository userRepository;
    private final MovieInfoRepository movieInfoRepository;

    public void addReview(ReviewInfoResponse reviewInfoResponse) {
        Long stars = reviewInfoResponse.getStars();
        Date createdDate = new Date(System.currentTimeMillis());
        Date lastUpdatedDate = null;
        Time createdTime = Time.valueOf(LocalTime.now());
        Time updatedTime = null;
        String content = reviewInfoResponse.getContent();
        Long likes = 0L;
        Long commentsCount = 0L;
        String usersWhoLikedIds = "";
        User user = findUserByUserId(reviewInfoResponse.getUserId());
        MovieInfo movieInfo = findMovieById(reviewInfoResponse.getMovieId());
        reviewInfoRepository.save(new ReviewInfo(stars,createdDate,lastUpdatedDate,createdTime,updatedTime,content,likes,commentsCount,usersWhoLikedIds,user,movieInfo));
    }

    private MovieInfo findMovieById(Long movieId) {
        MovieInfo movieInfo = new MovieInfo();
        Optional<MovieInfo> movieInfoOptional = movieInfoRepository.findById(movieId);
        if(movieInfoOptional.isPresent()){
            movieInfo = movieInfoOptional.get();
        }
        return movieInfo;
    }

    private User findUserByUserId(Long userId) {
        User user = null;
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        return user;
    }

    public void deleteReviewById(Long reviewId) {
        reviewInfoRepository.deleteById(reviewId);
    }

    public void editReviewById(Long reviewId, EditReviewInfoResponse reviewInfoResponse) {
        ReviewInfo reviewInfo = null;
        Optional<ReviewInfo> reviewInfoOptional = reviewInfoRepository.findById(reviewId);
        if(reviewInfoOptional.isPresent()){
            reviewInfo = reviewInfoOptional.get();
            reviewInfo.setContent(reviewInfoResponse.getContent());
            reviewInfo.setStars(reviewInfoResponse.getStars());
            reviewInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
            reviewInfo.setUpdatedTime(Time.valueOf(LocalTime.now()));
        }
        reviewInfoRepository.save(reviewInfo);
    }

    public ReviewInfoDto getReviewsByReviewId(Long reviewId) {
        ReviewInfo reviewInfo = null;
        Optional<ReviewInfo> optionalReviewInfo = reviewInfoRepository.findById(reviewId);
        if(optionalReviewInfo.isPresent()){
            reviewInfo = optionalReviewInfo.get();
        }
        return toReviewInfoDto(reviewInfo);
    }



    public List<ReviewInfoDto> getReviewsByMovieId(Long movieId) {
        List<ReviewInfo> reviewInfoList = reviewInfoRepository.findByMovieId(movieId);
        List<ReviewInfoDto> reviewInfoDtoList = toReviewInfoDtoList(reviewInfoList);
        Collections.reverse(reviewInfoDtoList);
        return reviewInfoDtoList;
    }

    public List<ReviewInfoDto> getReviewsByUserId(Long userId) {
        List<ReviewInfo> reviewInfoList = reviewInfoRepository.findByUserId(userId);
        List<ReviewInfoDto> reviewInfoDtoList = toReviewInfoDtoList(reviewInfoList);
        Collections.reverse(reviewInfoDtoList);
        return reviewInfoDtoList;
    }

    public List<ReviewInfoDto> getReviewsByMovieIdAndUsersIds(List<String> userIds, Long movieId) {
        List<Long> userIdsAsLong = parseToLong(userIds);
        List<ReviewInfo> reviewInfoList = reviewInfoRepository.findByMovieIdAndUsersIds(movieId,userIdsAsLong);
        List<ReviewInfoDto> reviewInfoDtoList = toReviewInfoDtoList(reviewInfoList);
        Collections.reverse(reviewInfoDtoList);
        return reviewInfoDtoList;
    }
    public List<ReviewInfoDto> getReviewsByUsersIds(List<String> userIds) {
        List<Long> userIdsAsLong = parseToLong(userIds);
        List<ReviewInfo> reviewInfoList = reviewInfoRepository.findByUsersIds(userIdsAsLong);
        List<ReviewInfoDto> reviewInfoDtoList = toReviewInfoDtoList(reviewInfoList);
        Collections.reverse(reviewInfoDtoList);
        return reviewInfoDtoList;
    }

    private List<Long> parseToLong(List<String> userIds) {
        List<Long> userIdsAsLong = new ArrayList<>();
        for(String userIdAsString: userIds){
            userIdsAsLong.add(Long.valueOf(userIdAsString));
        }
        return userIdsAsLong;
    }

    private List<ReviewInfoDto> toReviewInfoDtoList(List<ReviewInfo> reviewInfoList) {
        List<ReviewInfoDto> reviewInfoDtoList = new ArrayList<>();
        for (ReviewInfo reviewInfo : reviewInfoList) {
            reviewInfoDtoList.add(toReviewInfoDto(reviewInfo));
        }
        List<ReviewInfoDto> reversedList = new ArrayList<>(reviewInfoDtoList);
        Collections.reverse(reversedList);
        return reversedList;
    }

    private ReviewInfoDto toReviewInfoDto(ReviewInfo reviewInfo) {
        return new ReviewInfoDto(reviewInfo.getReviewId(),reviewInfo.getStars(),reviewInfo.getCreatedDate(),reviewInfo.getUpdatedDate(),reviewInfo.getCreatedTime(),reviewInfo.getUpdatedTime(),reviewInfo.getContent(),reviewInfo.getLikes(),reviewInfo.getCommentsCount(),reviewInfo.getUsersWhoLikedIds(),new UserInfo(reviewInfo.getUser().getUserId(),reviewInfo.getUser().getFirstName(),reviewInfo.getUser().getLastName(),reviewInfo.getUser().getUsername(),reviewInfo.getUser().getGender(),reviewInfo.getUser().getFriendsIds(),reviewInfo.getUser().getFollowing_ids(),reviewInfo.getUser().getFollowers_ids(),reviewInfo.getUser().getFollowers_count(),reviewInfo.getUser().getFollowing_count()),new MovieInfoDto(reviewInfo.getMovieInfo().getMovieId(),reviewInfo.getMovieInfo().getTitle(),reviewInfo.getMovieInfo().getReleaseYear(),reviewInfo.getMovieInfo().getOverview()));
    }


    public boolean isUserReviewedThisMovie(String movieId, String userId) {
        return reviewInfoRepository.isUserReviewedThisMovie(movieId,userId)==1;
    }


}
