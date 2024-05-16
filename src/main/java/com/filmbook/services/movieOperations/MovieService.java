package com.filmbook.services.movieOperations;

import com.filmbook.model.database.MovieInfo;
import com.filmbook.model.dto.MovieInfoDto;
import com.filmbook.repositories.MovieInfoRepository;
import com.filmbook.repositories.PhotoRepository;
import com.filmbook.repositories.ReviewInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieInfoRepository movieInfoRepository;
    private final PhotoRepository photoRepository;
    private final ReviewInfoRepository reviewInfoRepository;
    public byte[] getMoviePosterById(Long movieId) {
        return movieInfoRepository.getPosterImgById(movieId);
    }

    public MovieInfoDto getMovieInfoById(Long movieId) {
        MovieInfoDto movieInfoDto = null;
        Optional<MovieInfo> movieInfoOptional = movieInfoRepository.findById(movieId);
        if(movieInfoOptional.isPresent()){
            MovieInfo movieInfo = movieInfoOptional.get();
            movieInfoDto = getMovieInfoDto(movieInfo);
        }
        return movieInfoDto;
    }
    private MovieInfoDto getMovieInfoDto(MovieInfo movieInfo) {
        return new MovieInfoDto(movieInfo.getMovieId(), movieInfo.getTitle(),movieInfo.getReleaseYear(),movieInfo.getOverview());
    }

    public List<String> getMoviePhotosIdsByMovieId(Long movieId) {
        List<Long> photoIdsListAsLong= photoRepository.findPhotoIdsByMovieIds(movieId);
        List<String> photoIdsListAsString = new ArrayList<>();
        for (Long photoId : photoIdsListAsLong) {
            photoIdsListAsString.add(String.valueOf(photoId));
        }
        return photoIdsListAsString;
    }

    public byte[] getMoviePhotosByPhotoId(Long photoId) {
        return photoRepository.findContentById(photoId);
    }

    public Long getMovieAverageRating(Long movieId) {
        return reviewInfoRepository.getMovieAverageRating(movieId);
    }
}
