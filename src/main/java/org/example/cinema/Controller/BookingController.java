package org.example.cinema.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.cinema.Model.Bill;
import org.example.cinema.Model.Seat;
import org.example.cinema.Model.User;
import org.example.cinema.Service.BillService;
import org.example.cinema.Service.BookingService;
import org.example.cinema.Service.MovieService;
import org.example.cinema.Service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private SeatService seatService;
    @Autowired
    private BillService billService;
    @Autowired
    private MovieService movieService;
    @GetMapping("/{showId}")
    public String showBookingForm(@PathVariable Long showId, Model model) {
        List<Seat> seats = seatService.getSeatsByShowId(showId);
        Long movieId = bookingService.getMovieNameById(showId);
        String movieName = movieService.getMovieNameById(movieId);
        model.addAttribute("showId", showId);
        model.addAttribute("movieName", movieName);
        model.addAttribute("seats", seats);


        return "booking";
    }

    @PostMapping("/book")
    public String bookSeat(@RequestParam Long showId,
                           @RequestParam Long seatId,
                           @RequestParam String movieName,
                           @RequestParam double totalPrice,
                           HttpSession session,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        // Lấy User từ session
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập để đặt vé!");
            return "redirect:/login"; // Chuyển hướng đến trang đăng nhập
        }

        try {
            // Đặt ghế cho người dùng
            Seat bookedSeat = bookingService.bookSeat(showId, seatId, user);

            // Lấy số ghế đã đặt
            String seatNumber = bookedSeat.getSeatNumber();

            // Tạo hóa đơn
            Bill bill = new Bill(user, movieName, seatNumber, totalPrice);
            billService.createBill(bill);

            // Thêm hóa đơn vào model để hiển thị trên trang bill
            model.addAttribute("bill", bill);

            return "bill"; // Chuyển hướng sang trang hiển thị hóa đơn
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/booking/" + showId;
        }
    }




}