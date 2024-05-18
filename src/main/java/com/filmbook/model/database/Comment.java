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
    private Time createdTime;

    @Column(name = "updated_time")
    private Time updatedTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "review_info")
    private ReviewInfo reviewInfo;

    public Comment( String content, Long likes, String usersWhoLikedIds, Date createdDate, Date updatedDate, Time createdTime, Time updatedTime, User user, ReviewInfo reviewInfo) {
        this.content = content;
        this.likes = likes;
        this.usersWhoLikedIds = usersWhoLikedIds;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.user = user;
        this.reviewInfo = reviewInfo;
    }
}
