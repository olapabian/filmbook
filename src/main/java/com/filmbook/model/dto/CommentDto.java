package com.filmbook.model.dto;

import com.filmbook.model.requestsToSend.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
@Getter
@Setter
@AllArgsConstructor
public class CommentDto {

    private Long commentId;


    private String content;


    private Long likes;


    private String usersWhoLikedIds;


    private Date createdDate;


    private Date updatedDate;


    private Time createdTime;


    private Time updatedTime;


    private UserInfo userInfo;

    private ReviewInfoDto reviewInfoDto;

}
