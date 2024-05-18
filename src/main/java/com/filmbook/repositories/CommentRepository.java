package com.filmbook.repositories;

import com.filmbook.model.database.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM comment c WHERE c.reviewInfo.reviewId = ?1")
    List<Comment> findByReviewId(Long reviewId);
}
