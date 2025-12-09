package com.br.todoapi.todo_api.service;

import com.br.todoapi.todo_api.dto.response.MovieResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

    @Service
    public class MovieService {

        private final WebClient movieClient;

        public MovieService(@Qualifier("movieClient") WebClient movieClient) {
            this.movieClient = movieClient;
        }

        public MovieResponse findMovie(String query) {
            return movieClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/justwatch")
                            .queryParam("q", query)
                            .queryParam("L", "pt_br")
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(MovieResponse.class)
                    .block();
        }
    }