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
@Entity(name = "reviewed_movies")
public class ReviewedMovies {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reviewed_id")
    private Long reviewedId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieInfo movieInfo;
}
