package com.filmbook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class MovieInfoDto {

    private Long movieId;

    private String title;

    private Long releaseYear;

    private String overview;

}
