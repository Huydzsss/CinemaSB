package org.example.cinema.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "books")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show; // Liên kết với suất chiếu

    @OneToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat; // Liên kết với ghế ngồi

    @Column(nullable = false)
    private LocalDateTime bookingTime = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "users_id",nullable = true)
    private User user;
}
