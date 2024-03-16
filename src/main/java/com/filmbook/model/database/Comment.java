package com.filmbook.model.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "content")
    private String content;

    @Column(name = "likes")
    private Long likes;

    @Column(name = "users_who_liked_ids")
    private String usersWhoLikedIds; //oddzielone srednikiem czy cos

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
// moze nie byc
//    @ManyToOne
//    @JoinColumn(name = "movie_info")
//    private MovieInfo movieInfo;

    @ManyToOne
    @JoinColumn(name = "review_info")
    private ReviewInfo reviewInfo;

}
