package com.filmbook.repositories;

import com.filmbook.model.database.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieInfoRepository extends JpaRepository<MovieInfo, Long> {
//    @Query("SELECT m FROM movie_info m WHERE " +
//            "(LOWER(m.title) LIKE CONCAT(LOWER(:searchExpression), '%') OR " +
//            "UPPER(m.title) LIKE CONCAT(UPPER(:searchExpression), '%')) AND " +
//            "(CONCAT(LOWER(m.title), CAST(m.releaseYear AS string), LOWER(m.overview)) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
//            "CONCAT(UPPER(m.title), CAST(m.releaseYear AS string), UPPER(m.overview)) LIKE CONCAT('%', UPPER(:searchExpression), '%') OR " +
//            "CONCAT(LOWER(CAST(m.releaseYear AS string)), ' ', LOWER(m.title)) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
//            "CONCAT(UPPER(CAST(m.releaseYear AS string)), ' ', UPPER(m.title)) LIKE CONCAT('%', UPPER(:searchExpression), '%') OR " +
//            "CONCAT(LOWER(m.overview), ' ', LOWER(m.title)) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
//            "CONCAT(UPPER(m.overview), ' ', UPPER(m.title)) LIKE CONCAT('%', UPPER(:searchExpression), '%') OR " +
//            "CONCAT(LOWER(m.overview), ' ', LOWER(CAST(m.releaseYear AS string))) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
//            "CONCAT(UPPER(m.overview), ' ', UPPER(CAST(m.releaseYear AS string))) LIKE CONCAT('%', UPPER(:searchExpression), '%') OR " +
//            "CONCAT(LOWER(m.title), ' ', LOWER(m.overview)) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
//            "CONCAT(UPPER(m.title), ' ', UPPER(m.overview)) LIKE CONCAT('%', UPPER(:searchExpression), '%') OR " +
//            "CONCAT(LOWER(m.title), ' ', LOWER(CAST(m.releaseYear AS string))) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
//            "CONCAT(UPPER(m.title), ' ', UPPER(CAST(m.releaseYear AS string))) LIKE CONCAT('%', UPPER(:searchExpression), '%') OR " +
//            "CONCAT(LOWER(CAST(m.releaseYear AS string)), ' ', LOWER(m.overview)) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
//            "CONCAT(UPPER(CAST(m.releaseYear AS string)), ' ', UPPER(m.overview)) LIKE CONCAT('%', UPPER(:searchExpression), '%')) " +
//            "ORDER BY CASE WHEN LOWER(m.title) LIKE CONCAT(LOWER(:searchExpression), '%') THEN 0 ELSE 1 END, RANDOM() " +
//            "LIMIT 7"
//    )
@Query("SELECT m FROM MovieInfo m WHERE " +
        "(LOWER(m.title) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
        "LOWER(m.overview) LIKE CONCAT('%', LOWER(:searchExpression), '%')) " +
        "ORDER BY CASE WHEN LOWER(m.title) LIKE CONCAT(LOWER(:searchExpression), '%') THEN 0 ELSE 1 END, " +
        "FUNCTION('RAND') " + // Use RAND() function for random ordering
        "LIMIT :limit")
List<MovieInfo> findByTitleOrOverviewContaining(String searchExpression, int limit);


    @Query("SELECT m.posterImg FROM movie_info m WHERE m.movieId = :movieId")
    byte[] getPosterImgById(Long movieId);
}
