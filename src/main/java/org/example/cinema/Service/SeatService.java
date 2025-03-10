package org.example.cinema.Service;

import org.example.cinema.Model.Seat;
import org.example.cinema.Repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seat> getSeatsByShowId(Long showId) {
        List<Seat> seats = seatRepository.findByShowId(showId);
        System.out.println("Seats for Show " + showId + ": " + seats);
        return seats;
    }
}
