package com.br.todoapi.todo_api.dto.response;

import com.br.todoapi.todo_api.entity.Movie;
import lombok.Data;
import java.util.List;

@Data
public class MovieResponse {
        private boolean ok;
        private int error_code;
        private List<Movie> description;
    }



