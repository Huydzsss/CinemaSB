package org.example.cinema.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show; // Liên kết với suất chiếu

    @Column(name = "seat_number",nullable = false)
    private String seatNumber; // Số ghế (VD: A1, B2)

    @Column(nullable = false)
    private boolean isBooked = false; // Trạng thái ghế (true: đã đặt, false: còn trống)

    @OneToOne(mappedBy = "seat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Booking booking;
}
