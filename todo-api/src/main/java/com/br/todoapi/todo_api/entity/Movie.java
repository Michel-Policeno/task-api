package com.br.todoapi.todo_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private String id;
    private String type;
    private String url;
    private String title;
    private int year;
    private int runtime;
    @JsonProperty("photo_url")
    private List<String> photoUrl;
    private List<String> backdrops;
    private String tmdbId;
    private String imdbId;
    private float jwRating;
    private Integer tomatoMeter;
    private Boolean tomatoCertifiedFresh;

    private List<Offer> offers;

}



