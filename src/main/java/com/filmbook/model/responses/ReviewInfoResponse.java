package com.filmbook.model.responses;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewInfoResponse {
    private Long movieId;
    private Long userId;
    private Long stars;
    private String content;
}

