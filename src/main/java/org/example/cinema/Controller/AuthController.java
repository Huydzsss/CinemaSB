package org.example.cinema.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.cinema.Jwt.JwtUtil;
import org.example.cinema.Model.User;
import org.example.cinema.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password,
                                   HttpSession session) {
        User user = userService.getUserByUsername(username);

        if (user != null && userService.authenticateUser(username, password)) {
            session.setAttribute("loggedInUser", user);

            String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);

            return ResponseEntity.ok().headers(headers).body("Token: " + token);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tên đăng nhập hoặc mật khẩu!");
    }





    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String username,
                                      @RequestParam String password,
                                      @RequestParam String email) {
        if (userService.registerUser(username, password, email)) {
            User user = userService.getUserByUsername(username);
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);

            return ResponseEntity.ok().headers(headers).body("User registered successfully");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên người dùng đã tồn tại!");
    }


    public String home(HttpSession session, Model model) {
        Object userObj = session.getAttribute("loggedInUser");

        if (userObj instanceof User user) {
            model.addAttribute("user", user.getUsername());
        } else {
            model.addAttribute("user", null);
        }

        return "home"; // ✅ Đảm bảo view home được load
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
