//package com.filmbook.services;
//
//import jakarta.annotation.PostConstruct;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.stereotype.Service;
//import java.io.IOException;
//
//@Service
////nie dziala
//
//public class FilmWebScraper {
//    @PostConstruct
//    public void scrapeFilmData() {
//        try {
//            // Pobieranie zawartości strony
//            Document doc = Jsoup.connect("https://www.filmweb.pl/film/Zielona+mila-1999-862").get();
//
//            // Pobranie tytułu filmu
//            String title = doc.select("h1.filmTitle").text();
//            System.out.println("Tytuł: " + title);
//
//            // Pobranie oceny filmu
//            String rating = doc.select("span.filmRating__rateValue").text();
//            System.out.println("Ocena: " + rating);
//
//            // Pobranie reżysera
//            String director = doc.select("div.filmInfo__info--directors a").text();
//            System.out.println("Reżyser: " + director);
//
//            // Pobranie listy aktorów
//            Elements actorsElements = doc.select("span.filmInfo__info--castList a");
//            StringBuilder actors = new StringBuilder();
//            for (Element actorElement : actorsElements) {
//                actors.append(actorElement.text()).append(", ");
//            }
//            System.out.println("Aktorzy: " + actors);
//
//            // Pobranie opisu filmu
//            String description = doc.select("div.filmPosterSection__desc").text();
//            System.out.println("Opis: " + description);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
