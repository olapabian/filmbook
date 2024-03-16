package com.filmbook.repositories;

import com.filmbook.model.database.ReviewedMovies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewedMoviesRepository extends JpaRepository<ReviewedMovies, Long> {
}