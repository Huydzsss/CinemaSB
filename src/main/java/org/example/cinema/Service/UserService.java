package org.example.cinema.Service;

import org.example.cinema.Model.Role;
import org.example.cinema.Model.User;
import org.example.cinema.Repository.UserRespository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRespository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRespository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerUser(String username, String password, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            return false;
        }

        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        newUser.setEmail(email);
        newUser.setRole(Role.USER);

        userRepository.save(newUser);
        return true;
    }

    public boolean authenticateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
