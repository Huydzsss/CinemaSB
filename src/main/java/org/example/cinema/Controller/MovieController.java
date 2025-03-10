package org.example.cinema.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.cinema.Model.Movie;
import org.example.cinema.Model.User;
import org.example.cinema.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);

        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("user", user != null ? user.getUsername() : null);

        return "home";
    }
}
