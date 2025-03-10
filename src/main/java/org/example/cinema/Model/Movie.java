package org.example.cinema.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title; // Tên phim

    private String description; // Mô tả phim

    @Column(nullable = false)
    private int duration; // Thời lượng phim (phút)
    @Column(name = "poster",length = 255)
    private String posterUrl;
    @Column(nullable = false)
    private LocalDate releaseDate; // Ngày phát hành

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Show> shows;
}
