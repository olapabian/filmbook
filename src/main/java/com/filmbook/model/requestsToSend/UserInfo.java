package com.filmbook.model.requestsToSend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfo {
    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String gender;
    private String friendsIds;
    private String followingIds;
    private String followersIds;
    private Long followersCount;
    private Long followingCount;



}
