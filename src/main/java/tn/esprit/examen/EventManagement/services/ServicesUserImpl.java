package tn.esprit.examen.EventManagement.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.examen.EventManagement.dto.RegisterRequestDTO;
import tn.esprit.examen.EventManagement.dto.UserDTO;
import tn.esprit.examen.EventManagement.dto.UserUpdateDTO;
import tn.esprit.examen.EventManagement.entities.*;
import tn.esprit.examen.EventManagement.repositories.IUserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ServicesUserImpl implements IServicesUser {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User add(RegisterRequestDTO dto, MultipartFile profilePicture) throws IOException {
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

        // ✅ Handle profile picture
        if (profilePicture != null && !profilePicture.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + profilePicture.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/users/" + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(profilePicture.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            user.setProfilePicture("/users/" + fileName);
        }

        return userRepository.save(user);
    }

    public User updateUser(Long id, UserUpdateDTO dto, MultipartFile profilePicture) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✏️ Update fields
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setGenre(Genre.valueOf(dto.getGenre()));

        // ✅ Handle profile picture
        if (profilePicture != null && !profilePicture.isEmpty()) {
            try {
                // Delete old picture if exists
                if (user.getProfilePicture() != null) {
                    Path oldPath = Paths.get("src/main/resources/static" + user.getProfilePicture());
                    Files.deleteIfExists(oldPath);
                }

                // Save new picture
                String fileName = System.currentTimeMillis() + "_" + profilePicture.getOriginalFilename();
                Path path = Paths.get("src/main/resources/static/users/" + fileName);
                Files.createDirectories(path.getParent());
                Files.copy(profilePicture.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                user.setProfilePicture("/users/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to update profile picture", e);
            }
        }

        return userRepository.save(user);
    }


    @Override
    public String deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        String firstName = user.getFirstName();
        userRepository.delete(user);
        return "User " + firstName + " has been successfully deleted!";
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
