package com.filmbook.controllers.movie;

import com.filmbook.model.dto.MovieInfoDto;
import com.filmbook.services.movieOperations.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;
    @GetMapping("/getMoviePosterById/{movieId}")
    public ResponseEntity<byte[]> getMoviePoster(@PathVariable String movieId) {
        byte[] imageBytes = movieService.getMoviePosterById(Long.parseLong(movieId));
        if (imageBytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageBytes.length);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getMovieInfoById/{movieId}")
    public MovieInfoDto getMovieInfoById(@PathVariable String movieId) {
        return movieService.getMovieInfoById(Long.parseLong(movieId));
    }
    @GetMapping("/getMoviePhotosIdsByMovieId/{movieId}")
    public List<String> getMoviePhotosIdsByMovieId(@PathVariable String movieId) {
        return movieService.getMoviePhotosIdsByMovieId(Long.parseLong(movieId));
    }
    @GetMapping("/getMoviePhotosByPhotoId/{photoId}")
    public ResponseEntity<byte[]> getMoviePhotosByPhotoId(@PathVariable String photoId) {
        byte[] imageBytes = movieService.getMoviePhotosByPhotoId(Long.parseLong(photoId));
        if (imageBytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageBytes.length);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getMovieAverageRating/{movieId}")
    public Long getMovieAverageRating(@PathVariable String movieId){
        return movieService.getMovieAverageRating(Long.parseLong(movieId));
    }
}
