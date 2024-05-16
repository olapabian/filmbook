package com.filmbook.controllers.users;

import com.filmbook.model.responses.AddFollowResponse;
import com.filmbook.services.userOperations.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FollowController {
    private final FollowService followService;
    @PostMapping("/addFollow")
    public void addFollow(@RequestBody AddFollowResponse addFollowResponse){

        followService.addFollow(addFollowResponse.getFollowerId(), addFollowResponse.getFollowingId());
    }

    @PostMapping("/removeFollow")
    public void removeFollow(@RequestBody AddFollowResponse addFollowResponse){
        followService.removeFollow(addFollowResponse.getFollowerId(), addFollowResponse.getFollowingId());
    }

}
