package tn.esprit.examen.EventManagement.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.examen.EventManagement.dto.UserUpdateDTO;
import tn.esprit.examen.EventManagement.entities.*;
import tn.esprit.examen.EventManagement.repositories.IUserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ServicesUserImpl implements IServicesUser {

    private final IUserRepository userRepository;

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (dto.getFirstName() != null) user.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) user.setLastName(dto.getLastName());
        if (dto.getUsername() != null) user.setUsername(dto.getUsername());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPassword() != null){ user.setPassword(dto.getPassword());}
        if (dto.getDateOfBirth() != null) user.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getGenre() != null) user.setGenre(Enum.valueOf(Genre.class, dto.getGenre()));
        if (dto.getRole() != null) user.setRole(Enum.valueOf(Role.class, dto.getRole()));

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
