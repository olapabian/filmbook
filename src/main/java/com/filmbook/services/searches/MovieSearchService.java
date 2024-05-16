package com.filmbook.services.searches;

import com.filmbook.model.database.MovieInfo;
import com.filmbook.model.dto.MovieInfoDto;
import com.filmbook.repositories.MovieInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieSearchService {
    private final MovieInfoRepository movieInfoRepository;
    public List<MovieInfoDto> getMovieSearchResults(String searchExpression) {
        List<MovieInfo> userInfoList = movieInfoRepository.findByTitleOrOverviewContaining(searchExpression,50);
        return getMovieInfosDto(userInfoList);
    }
    public List<MovieInfoDto> getLiveMovieSearchResults(String searchExpression) {
        List<MovieInfo> userInfoList = movieInfoRepository.findByTitleOrOverviewContaining(searchExpression,7);
        return getMovieInfosDto(userInfoList);
    }
    private List<MovieInfoDto> getMovieInfosDto(List<MovieInfo> movieInfoList) {
        List<MovieInfoDto> movieInfoDtoList = new ArrayList<>();
        for(MovieInfo movieInfo: movieInfoList){
            movieInfoDtoList.add(getMovieInfoDto(movieInfo));
        }
        return movieInfoDtoList;
    }

    private MovieInfoDto getMovieInfoDto(MovieInfo movieInfo) {
        return new MovieInfoDto(movieInfo.getMovieId(), movieInfo.getTitle(),movieInfo.getReleaseYear(),movieInfo.getOverview());
    }



}
