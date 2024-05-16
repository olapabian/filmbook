package com.filmbook.repositories;

import com.filmbook.model.database.ReviewInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewInfoRepository extends JpaRepository<ReviewInfo, Long> {
    @Query("SELECT r FROM review_info r WHERE r.movieInfo.movieId = ?1 ORDER BY r.reviewId DESC")
    List<ReviewInfo> findByMovieId(Long movieId);

    @Query("SELECT r FROM review_info r WHERE r.user.userId = ?1 ORDER BY r.reviewId DESC")
    List<ReviewInfo> findByUserId(Long userId);

    @Query("SELECT r FROM review_info r WHERE r.movieInfo.movieId = ?1 AND r.user.userId IN ?2 ORDER BY r.reviewId DESC")
    List<ReviewInfo> findByMovieIdAndUsersIds(Long movieId, List<Long> userIds);

    @Query("SELECT r FROM review_info r WHERE r.user.userId IN ?1 ORDER BY r.reviewId DESC")
    List<ReviewInfo> findByUsersIds(List<Long> userIds);

    @Query("SELECT COUNT(r) FROM review_info r WHERE r.movieInfo.movieId = :movieId AND r.user.userId = :userId")
    int isUserReviewedThisMovie(@Param("movieId") String movieId, @Param("userId") String userId);


    @Query("SELECT AVG(r.stars) FROM review_info r WHERE r.movieInfo.movieId = :movieId")
    Long getMovieAverageRating(@Param("movieId") Long movieId);

}