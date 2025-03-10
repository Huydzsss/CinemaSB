package org.example.cinema.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    private String movieName;
    private String seatNumbers;
    private double totalPrice;
    private LocalDateTime bookingTime;

    public Bill() {
        this.bookingTime = LocalDateTime.now();
    }

    public Bill(User user, String movieName, String seatNumbers, double totalPrice) {
        this.user = user;
        this.movieName = movieName;
        this.seatNumbers = seatNumbers;
        this.totalPrice = totalPrice;
        this.bookingTime = LocalDateTime.now();
    }

    // Getters & Setters
}
