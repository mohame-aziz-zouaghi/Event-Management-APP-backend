package tn.esprit.examen.EventManagement.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.examen.EventManagement.Config.JwtUtil;
import tn.esprit.examen.EventManagement.dto.RegisterRequestDTO;
import tn.esprit.examen.EventManagement.entities.Genre;
import tn.esprit.examen.EventManagement.entities.Role;
import tn.esprit.examen.EventManagement.entities.User;
import tn.esprit.examen.EventManagement.repositories.IUserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                       IUserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User register(RegisterRequestDTO dto) {
        if(userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setGenre(Genre.valueOf(dto.getGenre()));
        user.setRole(Role.USER);
        user.setOrganizedEvents(new ArrayList<>());
        user.setReservations(new ArrayList<>());

        // Handle profile picture if provided
        if (dto.getProfilePicture() != null && !dto.getProfilePicture().isEmpty()) {
            try {
                String base64Image = dto.getProfilePicture();
                // remove the prefix if exists "data:image/png;base64,"
                if (base64Image.contains(",")) {
                    base64Image = base64Image.split(",")[1];
                }
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                String fileName = System.currentTimeMillis() + "_profile.png";
                Path path = Paths.get("src/main/resources/static/users/" + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, imageBytes);
                user.setProfilePicture("/users/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save profile picture");
            }
        }

        return userRepository.save(user);
    }

    public String login(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getId());
    }
}
