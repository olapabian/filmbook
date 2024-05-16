package com.filmbook.model.dto;

import com.filmbook.model.database.MovieInfo;
import com.filmbook.model.database.User;
import com.filmbook.model.requestsToSend.UserInfo;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
public class ReviewInfoDto {

    private Long reviewId;


    private Long stars;


    private Date createdDate;


    private Date updatedDate;


    private Time createdTime;


    private Time updatedTime;


    private String content;


    private Long likes;
    private Long commentsCount;


    private String usersWhoLikedIds;

    private UserInfo user;


    private MovieInfoDto movieInfo;

}
