package com.filmbook.repositories;

import com.filmbook.model.database.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieInfoRepository extends JpaRepository<MovieInfo, Long> {

    @Query("SELECT m FROM movie_info m WHERE" +
            "(LOWER(m.title) LIKE CONCAT('%', LOWER(:searchExpression), '%')) OR " +
            "LOWER(m.overview) LIKE CONCAT('%', LOWER(:searchExpression), '%')  " +
            "ORDER BY CASE WHEN LOWER(m.title) LIKE CONCAT(LOWER(:searchExpression), '%') THEN 0 ELSE 1 END " +
            "LIMIT :limit")
    List<MovieInfo> findMovies(String searchExpression, int limit);


    @Query("SELECT m.posterImg FROM movie_info m WHERE m.movieId = :movieId")
    byte[] getPosterImgById(Long movieId);
}

