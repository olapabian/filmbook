package com.filmbook.model.dto;

import lombok.*;

@AllArgsConstructor
@Builder
@Data
@Getter
public class UserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String gender;
    private byte[] userImage;
    private String friendsIds;
    private String following_ids;
    private String followers_ids;
    private Long following_count;
    private Long followers_count;
    private String token;
    public UserDto(Long userId, String firstName, String lastName, String username, String gender, byte[] userImage, String friendsIds) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.gender = gender;
        this.userImage = userImage;
        this.friendsIds = friendsIds;
        this.followers_count=0L;
        this.following_count=0L;
    }

    public UserDto(String username) {
        this.username = username;
    }


}
