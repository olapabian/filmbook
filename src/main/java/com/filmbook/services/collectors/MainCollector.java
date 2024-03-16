package com.filmbook.services.collectors;

import com.filmbook.jsons.DescriptionRequest;
import com.filmbook.jsons.InfoRequest;
import com.filmbook.jsons.PhotosRequest;
import com.filmbook.jsons.RatingRequest;
import com.filmbook.model.database.MovieInfo;
import com.filmbook.model.database.Photo;
import com.filmbook.model.others.InfoAndBool;
import com.filmbook.repositories.MovieInfoRepository;
import com.filmbook.repositories.PhotoRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

@Service
public class MainCollector {
    private final MovieInfoRepository movieInfoRepository;
    private final PhotoRepository photoRepository;

    public MainCollector(MovieInfoRepository movieInfoRepository, PhotoRepository photoRepository) {
        this.movieInfoRepository = movieInfoRepository;
        this.photoRepository = photoRepository;
    }

//    @PostConstruct
    public void collect() throws IOException {
//        for (int filmId = 1; filmId <= 900_000; filmId++)
        for (int filmId = 1; filmId <= 20; filmId++)
        {
            InfoAndBool infoAndBool = isCorrectMovie(String.valueOf(filmId));
            if(infoAndBool.getFlag()){
//                System.out.println(infoRequest.getTitle());
                MovieInfo movieInfo = collectMovie(String.valueOf(filmId),infoAndBool.getInfoRequest());
                collectPhotos(String.valueOf(filmId),movieInfo);
            }
        }
    }
    //Checking is that movie and popularity
    public InfoAndBool isCorrectMovie(String filmId) throws IOException {
        InfoAndBool infoAndBool = new InfoAndBool();
        RatingRequest ratingRequest = new RatingRequest();
        String rating;
        double Rating = 0;

        // -----------------Request rating rating and rating count needed to check popularity--------------------------------------------------
        URL url = new URL("https://www.filmweb.pl/api/v1/film/" + filmId + "/rating");
        HttpCollector ratingCollector = new HttpCollector(url);
        ratingCollector.Collect();

        if (!ratingCollector.getResponse().isEmpty()) {
            String response = String.valueOf(ratingCollector.getResponse());
            Gson gson = new Gson();
            ratingRequest = gson.fromJson(response, RatingRequest.class);
            rating = String.valueOf(ratingRequest.getRate());
            Rating = Double.parseDouble(rating);
        } else {
            infoAndBool.setFlag(false);
        }

        if (Rating < 5.0) {
            infoAndBool.setFlag(false);
        }

        if (ratingRequest.getCount() < 6000) {
            infoAndBool.setFlag(false);
        }

        //----------------------------Request info to check is that film-------------------------------------
        URL url1 = new URL("https://www.filmweb.pl/api/v1/film/" + filmId + "/info");
        HttpCollector infoCollector = new HttpCollector(url1);
        infoCollector.Collect();

        if (!infoCollector.getResponse().isEmpty()) {
            String response1 = String.valueOf(infoCollector.getResponse());
            Gson gson1;
            gson1 = new Gson();
            InfoRequest infoRequest = gson1.fromJson(response1, InfoRequest.class);
            infoAndBool.setFlag(infoRequest.getType().equals("film"));
            infoAndBool.setInfoRequest(infoRequest);
        }
        return infoAndBool;
    }
    public MovieInfo collectMovie(String filmId, InfoRequest infoRequest) {
        MovieInfo movieInfo = new MovieInfo();
        try {


            //title
            movieInfo.setTitle(infoRequest.getTitle());


            //release year
            movieInfo.setReleaseYear(infoRequest.getYear());


            //poster Img
            String posterPath = infoRequest.getPosterPath().replace("$", "1");
            URL postreImgUrl = new URL("https://fwcdn.pl/fpo" + posterPath);

            System.out.println(postreImgUrl);
            HttpCollector posterCollector = new HttpCollector(postreImgUrl);
            posterCollector.Collect();


            //collecting table of bites
            byte[] posterImgBytes = posterCollector.getResponse().toString().getBytes();

            if (posterImgBytes.length > 0) {
                movieInfo.setPosterImg(posterImgBytes);
            }


            //description
            URL descriptionUrl = new URL("https://www.filmweb.pl/api/v1/film/" + filmId + "/description");
            HttpCollector descriptionCollector = new HttpCollector(descriptionUrl);
            descriptionCollector.Collect();
            if (!descriptionCollector.getResponse().isEmpty()) {
                String response1 = String.valueOf(descriptionCollector.getResponse());
                Gson gson1;
                gson1 = new Gson();
                DescriptionRequest descriptionRequest = gson1.fromJson(response1, DescriptionRequest.class);
                movieInfo.setOverview(descriptionRequest.getSynopsis());
            }

            //saving
            movieInfoRepository.save(movieInfo);


        }
        catch (Exception e){
            System.out.println(e.getMessage());
            collectMovie(filmId,infoRequest);
        }
        return movieInfo;
    }

    public void collectPhotos(String filmId, MovieInfo movieInfo) {
        try {
            URL photosUrl = new URL("https://www.filmweb.pl/api/v1/film/" + filmId + "/photos");
            HttpCollector photosCollector = new HttpCollector(photosUrl);
            photosCollector.Collect();
            if (!photosCollector.getResponse().isEmpty()) {
                String response1 = String.valueOf(photosCollector.getResponse());
                Gson gson1;
                gson1 = new Gson();
                Type photosRequestListType = new TypeToken<List<PhotosRequest>>(){}.getType();
                List<PhotosRequest> photosRequestList = gson1.fromJson(response1, photosRequestListType);
                for (PhotosRequest photosRequest : photosRequestList) {
                    Photo photo = new Photo();
                    photo.setMovieInfo(movieInfo);

                    //photo collect fro url
                    String photoPath = photosRequest.getSourcePath().replace("$", "1");
                    URL photoImgUrl = new URL("https://fwcdn.pl/fph" + photoPath);

                    System.out.println(photoImgUrl);
                    HttpCollector photoCollector = new HttpCollector(photoImgUrl);
                    photoCollector.Collect();

                    //collecting table of bites
                    byte[] photoImgBytes = photoCollector.getResponse().toString().getBytes();

                    if (photoImgBytes.length > 0) {
                        photo.setContent(photoImgBytes);
                    }
                    photoRepository.save(photo);
                }
            }
            System.out.println(movieInfo.getTitle());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            collectPhotos(filmId, movieInfo);
        }
    }
}
