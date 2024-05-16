package com.filmbook.model.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddFollowResponse {
    private Long followerId;
    private Long followingId;

}
