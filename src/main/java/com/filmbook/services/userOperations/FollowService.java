package com.filmbook.services.userOperations;

import com.filmbook.model.database.User;
import com.filmbook.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    public void  addFollow(Long followerId, Long followingId){
        Optional<User> followerUserOptional = userRepository.findById(followerId);
        if(followerUserOptional.isPresent()){
            String newFollowingString ="";
            User followerUser = followerUserOptional.get();
            String followingString = followerUser.getFollowing_ids();
            if(followingString!=null && !followingString.isEmpty()){
                newFollowingString = followingString + followingId + ";";
            }
            else {
                newFollowingString =followingId + ";";
            }
            followerUser.setFollowing_count(followerUser.getFollowing_count()+1);
            followerUser.setFollowing_ids(newFollowingString);
            userRepository.save(followerUser);
        }
        Optional<User> followingUserOptional = userRepository.findById(followingId);
        if(followingUserOptional.isPresent()){
            User followingUser = followingUserOptional.get();
            String followersString = followingUser.getFollowers_ids();
            String newFollowersString = "";
            if(followersString!=null && !followersString.isEmpty()){
                newFollowersString = followersString + followerId + ";";
            }
            else newFollowersString = followerId + ";";
            followingUser.setFollowers_count(followingUser.getFollowers_count()+1);
            followingUser.setFollowers_ids(newFollowersString);
            userRepository.save(followingUser);
        }
    }

    public void removeFollow(Long followerId, Long followingId) {
        Optional<User> followerUserOptional = userRepository.findById(followerId);
        if(followerUserOptional.isPresent()){
            User followerUser = followerUserOptional.get();
            String followingString = followerUser.getFollowing_ids();

            String newFollowingString = newFollowString(followingString, followingId);

            followerUser.setFollowing_count(followerUser.getFollowing_count()-1);
            followerUser.setFollowing_ids(newFollowingString);
            userRepository.save(followerUser);
        }
        Optional<User> followingUserOptional = userRepository.findById(followingId);
        if(followingUserOptional.isPresent()){
            User followingUser = followingUserOptional.get();
            String followersString = followingUser.getFollowers_ids();
            String newFollowersString = newFollowString(followersString,followerId);
            followingUser.setFollowers_count(followingUser.getFollowers_count()-1);
            followingUser.setFollowers_ids(newFollowersString);
            userRepository.save(followingUser);
        }
    }

    private String newFollowString(String string, Long idToRemove) {
        StringBuilder result = new StringBuilder();
        String[] ids = string.split(";");

        for (String id : ids) {
            Long idValue;
            try {
                idValue = Long.parseLong(id.trim());
            } catch (NumberFormatException e) {
                continue;
            }
            if (idValue.equals(idToRemove)) {
                continue;
            }
            result.append(id).append(";");
        }


        return result.toString();
    }

}
