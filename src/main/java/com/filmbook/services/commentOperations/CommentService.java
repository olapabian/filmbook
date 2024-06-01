package com.filmbook.services.commentOperations;

import com.filmbook.model.database.Comment;
import com.filmbook.model.database.ReviewInfo;
import com.filmbook.model.database.User;
import com.filmbook.model.dto.CommentDto;
import com.filmbook.model.dto.MovieInfoDto;
import com.filmbook.model.dto.ReviewInfoDto;
import com.filmbook.model.requestsToSend.UserInfo;
import com.filmbook.repositories.CommentRepository;
import com.filmbook.repositories.ReviewInfoRepository;
import com.filmbook.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReviewInfoRepository reviewInfoRepository;
    public List<CommentDto> getCommentsByReviewId(String reviewId) {
       List<Comment> commentList =  commentRepository.findByReviewId(Long.valueOf(reviewId));
       return toCommentDtoList(commentList);
    }

    private List<CommentDto> toCommentDtoList(List<Comment> commentList) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        for(Comment comment: commentList){
            commentDtoList.add(toCommentDto(comment));
        }
        return commentDtoList;
    }

    private CommentDto toCommentDto(Comment comment) {
        return new CommentDto(comment.getCommentId(),comment.getContent(),comment.getLikes(),comment.getUsersWhoLikedIds(),comment.getCreatedDate(),comment.getUpdatedDate(),comment.getCreatedTime(),comment.getUpdatedTime(),new UserInfo(comment.getUser().getUserId(),comment.getUser().getFirstName(),comment.getUser().getLastName(),comment.getUser().getUsername(),comment.getUser().getGender(),comment.getUser().getFriendsIds(),comment.getUser().getFollowing_ids(),comment.getUser().getFollowers_ids(),comment.getUser().getFollowers_count(),comment.getUser().getFollowing_count()),toReviewInfoDto(comment.getReviewInfo()));
    }
    private ReviewInfoDto toReviewInfoDto(ReviewInfo reviewInfo) {
        return new ReviewInfoDto(reviewInfo.getReviewId(),reviewInfo.getStars(),reviewInfo.getCreatedDate(),reviewInfo.getUpdatedDate(),reviewInfo.getCreatedTime(),reviewInfo.getUpdatedTime(),reviewInfo.getContent(),reviewInfo.getLikes(),reviewInfo.getCommentsCount(),reviewInfo.getUsersWhoLikedIds(),new UserInfo(reviewInfo.getUser().getUserId(),reviewInfo.getUser().getFirstName(),reviewInfo.getUser().getLastName(),reviewInfo.getUser().getUsername(),reviewInfo.getUser().getGender(),reviewInfo.getUser().getFriendsIds(),reviewInfo.getUser().getFollowing_ids(),reviewInfo.getUser().getFollowers_ids(),reviewInfo.getUser().getFollowers_count(),reviewInfo.getUser().getFollowing_count()),new MovieInfoDto(reviewInfo.getMovieInfo().getMovieId(),reviewInfo.getMovieInfo().getTitle(),reviewInfo.getMovieInfo().getReleaseYear(),reviewInfo.getMovieInfo().getOverview()));
    }

    public void addComment(Long reviewId, Long userId, String content) {
        // Usunięcie pierwszego i ostatniego znaku z łańcucha content
        if (content.length() > 1) {
            content = content.substring(1, content.length() - 1);
        } else {
            content = "";
        }

        ReviewInfo reviewInfo = findReview(reviewId);
        reviewInfo.setCommentsCount(reviewInfo.getCommentsCount() + 1);
        User user = findUser(userId);
        Long likes = 0L;
        java.sql.Date createdDate = new java.sql.Date(System.currentTimeMillis());
        Date lastUpdatedDate = null;
        Time createdTime = Time.valueOf(LocalTime.now());
        Time updatedTime = null;
        reviewInfoRepository.save(reviewInfo);
        commentRepository.save(new Comment(content, likes, "", createdDate, lastUpdatedDate, createdTime, updatedTime, user, reviewInfo));
    }


    private User findUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        else return null;
    }

    private ReviewInfo findReview(Long reviewId) {
        Optional<ReviewInfo> reviewInfoOptional = reviewInfoRepository.findById(reviewId);
        if(reviewInfoOptional.isPresent())
        {
            return reviewInfoOptional.get();
        }
        else return null;
    }

    public void deleteComment(Long commentId) {
        Comment comment = findComment(commentId);
        ReviewInfo reviewInfo = findReview(comment.getReviewInfo().getReviewId());
        reviewInfo.setCommentsCount(reviewInfo.getCommentsCount()-1);
        reviewInfoRepository.save(reviewInfo);
        commentRepository.deleteById(commentId);
    }

    private Comment findComment(Long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if(commentOptional.isPresent()){
            Comment comment = commentOptional.get();
            return comment;
        }
        return null;
    }
}
