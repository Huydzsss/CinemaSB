package org.example.cinema.Service;

import org.example.cinema.Model.Movie;
import org.example.cinema.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
        @Autowired
        private MovieRepository movieRepository;

        public List<Movie> getAllMovies() {
            return movieRepository.findAll();
        }
    public String getMovieNameById(Long movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        return movie.map(Movie::getTitle).orElse("Phim chưa cập nhật");
    }
}
