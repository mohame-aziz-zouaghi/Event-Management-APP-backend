package tn.esprit.examen.EventManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.EventManagement.entities.User;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    // Check if a username already exists
    boolean existsByUsername(String username);

    // Find user by username
    Optional<User> findByUsername(String username);
    User findByEmail(String email);
}