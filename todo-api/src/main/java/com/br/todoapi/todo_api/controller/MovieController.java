package com.br.todoapi.todo_api.controller;

import com.br.todoapi.todo_api.dto.response.FoodResponse;
import com.br.todoapi.todo_api.dto.response.MovieResponse;
import com.br.todoapi.todo_api.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/movies/")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "movies", description = "Endpoints para chama API publica - https://imdb.iamidiotareyoutoo.com/")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("{movie}")
    @Operation(summary = "Buscar filmes e series", description = "Retorna detalhes e onde assistir o filme/serie escolhido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "filmes/series retornadas"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<MovieResponse> findMovies (@PathVariable String movie) {
        MovieResponse moviesList = movieService.findMovie(movie);
        return ResponseEntity.ok(moviesList);
    }
}
