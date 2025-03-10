package org.example.cinema.Service;

import jakarta.transaction.Transactional;
import org.example.cinema.Model.Booking;
import org.example.cinema.Model.Seat;
import org.example.cinema.Model.Show;
import org.example.cinema.Model.User;
import org.example.cinema.Repository.BookingRepository;
import org.example.cinema.Repository.SeatRepository;
import org.example.cinema.Repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowRepository showRepository;

    @Transactional
    public Seat bookSeat(Long showId, Long seatId, User user) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy suất chiếu!"));

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Ghế không tồn tại!"));

        if (seat.isBooked()) {
            throw new RuntimeException("Ghế đã được đặt!");
        }

        seat.setBooked(true);
        seatRepository.save(seat); // Cập nhật trạng thái ghế

        Booking booking = new Booking();
        booking.setShow(show);
        booking.setSeat(seat);
        booking.setUser(user); // ✅ Gán user để tránh lỗi users_id NULL

        bookingRepository.save(booking); // Lưu đơn đặt vé
        return seat;
    }




    public Long getMovieNameById(Long showId) {
        Optional<Show> show = showRepository.findById(showId);
        return show.map(Show::getMovieId).orElse(null);
    }
}
