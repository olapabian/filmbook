package com.filmbook.controllers.users;

import com.filmbook.model.requestsToSend.UserInfo;
import com.filmbook.services.searches.PeopleSearchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class PeopleSearchController {
    private final PeopleSearchService peopleSearchService;

    @PostMapping("/livePeopleSearchResults")
    private List<UserInfo> getLivePeopleSearchResults(@RequestBody Map<String, String> requestBody) {
        String searchExpression = requestBody.get("searchExpression");
        System.out.println(searchExpression);
        List<UserInfo> userInfoList = peopleSearchService.getPeopleSearchResults(searchExpression);
        System.out.println(userInfoList);
        if (userInfoList.size() >= 9) {
            return userInfoList.subList(0, 9);
        } else {
            return userInfoList;
        }
    }

    @PostMapping("/peopleSearchResults")
    private List<UserInfo> getPeopleSearchResults(@RequestBody Map<String, String> requestBody) {
        String searchExpression = requestBody.get("searchExpression");
        System.out.println(searchExpression);
        List<UserInfo> userInfoList = peopleSearchService.getPeopleSearchResults(searchExpression);
        System.out.println(userInfoList);
        return userInfoList;
    }
}
