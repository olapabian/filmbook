package com.filmbook.model.database;

import com.filmbook.model.database.MovieInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "photo_id")
    private Long photoId;

    @Column(name = "content")
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "movie_info")
    private MovieInfo movieInfo;
}
