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
import java.net.HttpURLConnection;
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
        //te jeszcze sa pominiete
        for (int filmId = 153_508; filmId <= 392_921; filmId++)

//        for (int filmId = 1; filmId <= 20; filmId++)
        {
            InfoAndBool infoAndBool = isCorrectMovie(String.valueOf(filmId));
            if(infoAndBool.getFlag()){
                MovieInfo movieInfo = collectMovie(String.valueOf(filmId),infoAndBool.getInfoRequest());
                collectPhotos(String.valueOf(filmId),movieInfo);
            }
            System.out.println(filmId);
        }
    }
    public InfoAndBool isCorrectMovie(String filmId) throws IOException {
        InfoAndBool infoAndBool = new InfoAndBool();
//        RatingRequest ratingRequest = new RatingRequest();
//        String rating;
//        double Rating = 0;
//
//        // -----------------Request rating rating and rating count needed to check popularity--------------------------------------------------
//        URL url = new URL("https://www.filmweb.pl/api/v1/film/" + filmId + "/rating");
//        HttpCollector ratingCollector = new HttpCollector(url);
//        ratingCollector.collect();
//
//        if (!ratingCollector.getResponse().isEmpty()) {
//            String response = String.valueOf(ratingCollector.getResponse());
//            Gson gson = new Gson();
//            ratingRequest = gson.fromJson(response, RatingRequest.class);
//            rating = String.valueOf(ratingRequest.getRate());
//            Rating = Double.parseDouble(rating);
//        } else {
//            infoAndBool.setFlag(false);
//        }
//
//        if (Rating < 5.0) {
//            infoAndBool.setFlag(false);
//        }
//
//        if (ratingRequest.getCount() < 6000) {
//            infoAndBool.setFlag(false);
//        }

        //----------------------------Request info to check is that film-------------------------------------
        URL url1 = new URL("https://www.filmweb.pl/api/v1/film/" + filmId + "/info");
        HttpCollector infoCollector = new HttpCollector(url1);
        infoCollector.collect();

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
            // Ustawienie tytułu
            movieInfo.setTitle(infoRequest.getTitle());

            // Ustawienie roku wydania
            movieInfo.setReleaseYear(infoRequest.getYear());

            // Sprawdzenie, czy ścieżka plakatu jest dostępna
            if (infoRequest.getPosterPath() != null && !infoRequest.getPosterPath().isEmpty()) {
                // Tworzenie URL do plakatu
                String posterPath = infoRequest.getPosterPath().replace("$", "2");
                URL posterImgUrl = new URL("https://fwcdn.pl/fpo" + posterPath);

                // Wyświetlenie URL plakatu
                System.out.println("URL plakatu: " + posterImgUrl);

                // Inicjalizacja kolektora HTTP dla plakatu
                HttpCollector posterCollector = new HttpCollector(posterImgUrl);

                // Pobranie kodu odpowiedzi HTTP
                posterCollector.collect();
                int responseCode = posterCollector.getResponseCode();

                // Jeśli kod odpowiedzi to 403 (Forbidden), pomiń pobieranie obrazka plakatu
                if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                    System.out.println("Odmowa dostępu do plakatu. Kod statusu: " + responseCode);
                } else if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Pobranie obrazka plakatu jako tablicy bajtów, jeśli odpowiedź jest OK (200)
                    byte[] posterImgBytes = posterCollector.getResponseBytes();

                    // Jeśli obrazek plakatu został pomyślnie pobrany, ustaw go w obiekcie MovieInfo
                    if (posterImgBytes != null && posterImgBytes.length > 0) {
                        movieInfo.setPosterImg(posterImgBytes);
                    }
                } else {
                    System.out.println("Nieudane połączenie dla obrazka plakatu. Kod statusu: " + responseCode);
                }
            }

            // Pobranie opisu filmu
            URL descriptionUrl = new URL("https://www.filmweb.pl/api/v1/film/" + filmId + "/description");
            HttpCollector descriptionCollector = new HttpCollector(descriptionUrl);
            descriptionCollector.collect();

            // Jeśli odpowiedź jest niepusta, sparsuj ją do obiektu DescriptionRequest i ustaw opis w obiekcie MovieInfo
            if (!descriptionCollector.getResponse().isEmpty()) {
                String response1 = String.valueOf(descriptionCollector.getResponse());
                Gson gson1 = new Gson();
                DescriptionRequest descriptionRequest = gson1.fromJson(response1, DescriptionRequest.class);
                movieInfo.setOverview(descriptionRequest.getSynopsis());
            }

            // Zapisanie obiektu MovieInfo do bazy danych
            movieInfoRepository.save(movieInfo);

        } catch (Exception e) {
            // Obsługa wyjątków
            System.out.println("Wystąpił błąd: " + e.getMessage());
            // W razie błędu, rekurencyjnie wywołaj funkcję collectMovie dla tego samego filmu
            collectMovie(filmId, infoRequest);
        }
        // Zwrócenie obiektu MovieInfo
        return movieInfo;
    }


    public void collectPhotos(String filmId, MovieInfo movieInfo) {
        try {
            URL photosUrl = new URL("https://www.filmweb.pl/api/v1/film/" + filmId + "/photos");
            HttpCollector photosCollector = new HttpCollector(photosUrl);
            photosCollector.collect();
            if (!photosCollector.getResponse().isEmpty()) {
                String response1 = String.valueOf(photosCollector.getResponse());
                Gson gson1;
                gson1 = new Gson();
                Type photosRequestListType = new TypeToken<List<PhotosRequest>>(){}.getType();
                List<PhotosRequest> photosRequestList = gson1.fromJson(response1, photosRequestListType);
                for (PhotosRequest photosRequest : photosRequestList) {
                    Photo photo = new Photo();
                    photo.setMovieInfo(movieInfo);
                    if(photosRequest.getSourcePath()!=null && !photosRequest.getSourcePath().isEmpty()){
                        //photo collect fro url
                        String photoPath = photosRequest.getSourcePath().replace("$", "1");
                        URL photoImgUrl = new URL("https://fwcdn.pl/fph" + photoPath);

                        System.out.println(photoImgUrl);
                        HttpCollector photoCollector = new HttpCollector(photoImgUrl);

                        // Pobieranie obrazka plakatu jako tablicy bajtów
                        byte[] photoImgBytes = null;
                        try {
                            photoImgBytes = photoCollector.getResponseBytes();
                        } catch (IOException e) {
                            // Jeśli kod odpowiedzi to 403 (Forbidden), pomiń pobieranie obrazka
                            if (photoCollector.getResponseCode() == HttpURLConnection.HTTP_FORBIDDEN) {
                                System.out.println("Odmowa dostępu do zdjęcia. Kod statusu: " + HttpURLConnection.HTTP_FORBIDDEN);
                                continue; // Pomijanie dalszych kroków i przechodzenie do następnej iteracji pętli
                            } else {
                                e.printStackTrace();
                            }
                        }

                        if (photoImgBytes != null && photoImgBytes.length > 0) {
                            photo.setContent(photoImgBytes);
                        }
                        photoRepository.save(photo);
                    }

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
