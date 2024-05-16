package com.filmbook.controllers.movie;

import com.filmbook.model.dto.MovieInfoDto;
import com.filmbook.services.searches.MovieSearchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class MovieSearchController {
    private final MovieSearchService movieSearchService;

    @PostMapping("/liveMovieSearchResults")
    private List<MovieInfoDto> getLiveMovieSearchResults(@RequestBody Map<String, String> requestBody) {
        String searchExpression = requestBody.get("searchExpression");
        System.out.println(searchExpression);
        List<MovieInfoDto> movieInfoList = movieSearchService.getLiveMovieSearchResults(searchExpression);
        System.out.println(movieInfoList);
        return movieInfoList;

    }

    @PostMapping("/movieSearchResults")
    private List<MovieInfoDto> getMovieSearchResults(@RequestBody Map<String, String> requestBody) {
        String searchExpression = requestBody.get("searchExpression");
        System.out.println(searchExpression);
        List<MovieInfoDto> movieInfoList = movieSearchService.getMovieSearchResults(searchExpression);
        System.out.println(movieInfoList);
        return movieInfoList;
    }
}
