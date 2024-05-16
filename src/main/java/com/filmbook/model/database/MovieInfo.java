package com.filmbook.model.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "movie_info")
public class MovieInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "title")
    private String title;

    @Column(name = "release_year")
    private Long releaseYear;

    @Column(name = "overview", length = 10000) // dla przykładu, zwiększam maksymalną długość do 1000 znaków
    private String overview;

    @Column(name = "poster_img")
    private byte[] posterImg;

    public MovieInfo(String title) {
        this.title=title;
    }
}
