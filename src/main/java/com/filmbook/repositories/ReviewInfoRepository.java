package com.filmbook.repositories;

import com.filmbook.model.database.ReviewInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewInfoRepository extends JpaRepository<ReviewInfo, Long> {
}