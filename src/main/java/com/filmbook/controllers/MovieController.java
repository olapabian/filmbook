//package com.filmbook.controllers;
//
//import com.filmbook.model.database.MovieInfo;
//import com.filmbook.repositories.MovieInfoRepository;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/movie")
//public class MovieController {
//    @Autowired
//    private MovieInfoRepository movieRepository;
//
//    @GetMapping
//    public List<MovieInfo> list() {
//        return movieRepository.findAll();
//    }
//
//    @GetMapping("{id}")
//    public Optional<MovieInfo> getMovie(@PathVariable Long id) {
//        return movieRepository.findById(id);
//    }
//
//    @DeleteMapping("{id}")
//    public void deleteMovie(@PathVariable Long id) {
//        movieRepository.deleteById(id);
//    }
//
//    @PostMapping
//    public MovieInfo addMovie(@RequestBody String title) {
//        return movieRepository.saveAndFlush(new MovieInfo(title));
//    }
//
//    @PutMapping("{id}")
//    public MovieInfo updateTask(@PathVariable Long id, @RequestBody MovieInfo movie) {
//        Optional<MovieInfo> currentMovieOptional = movieRepository.findById(id);
//        MovieInfo currentMovie = null;
//        if (currentMovieOptional.isPresent()) {
//            currentMovie = currentMovieOptional.get();
//            BeanUtils.copyProperties(movie, currentMovie, "movie_id");
//        }
//        return movieRepository.saveAndFlush(currentMovie);
//    }
//}
