package com.filmbook.services.searches;

import com.filmbook.model.database.User;
import com.filmbook.model.requestsToSend.UserInfo;
import com.filmbook.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PeopleSearchService {
    private final UserRepository userRepository;

    public List<UserInfo> getPeopleSearchResults(String searchExpression) {
        List<User> userList = userRepository.findByUsernameOrFirstNameOrLastNameContaining(searchExpression);
        List<UserInfo> userInfoList = getUserInfos(userList);
        return userInfoList;
    }


    private List<UserInfo> getUserInfos(List<User> userList) {
        List<UserInfo> userInfos = new ArrayList<>();
        for (User user : userList) {
            userInfos.add(getUserInfo(user));
        }
        return userInfos;
    }

    public UserInfo getUserInfo(User user) {
        return new UserInfo(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getGender(), user.getFriendsIds(), user.getFollowing_ids(),user.getFollowers_ids(),user.getFollowers_count(),user.getFollowing_count());
    }



}
