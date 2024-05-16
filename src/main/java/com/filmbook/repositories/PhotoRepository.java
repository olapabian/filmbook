package com.filmbook.repositories;

import com.filmbook.model.database.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Query("SELECT photoId from photo where movieInfo.movieId = ?1")
    List<Long> findPhotoIdsByMovieIds(Long movieId);
    @Query("SELECT content from photo where photoId = ?1")
    byte[] findContentById(Long photoId);
}