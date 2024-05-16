package com.filmbook.model.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "review_info")
public class ReviewInfo {
    public ReviewInfo( Long stars, Date createdDate, Date updatedDate, Time createdTime, Time updatedTime, String content, Long likes, Long commentsCount, String usersWhoLikedIds, User user, MovieInfo movieInfo) {
        this.stars = stars;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.content = content;
        this.likes = likes;
        this.commentsCount = commentsCount;
        this.usersWhoLikedIds = usersWhoLikedIds;
        this.user = user;
        this.movieInfo = movieInfo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "stars")
    private Long stars;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "created_time")
    private Time createdTime;

    @Column(name = "updated_time")
    private Time updatedTime;

    @Column(name = "content", length = 10000)
    private String content;

    @Column(name = "likes")
    private Long likes;
    @Column(name = "comments_count")
    private Long commentsCount;

    @Column(name = "users_who_liked_ids")
    private String usersWhoLikedIds; //oddzielone srednikiem czy cos

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_info")
    private MovieInfo movieInfo;
}
