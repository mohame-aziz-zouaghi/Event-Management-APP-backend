package tn.esprit.examen.EventManagement.services;

import tn.esprit.examen.EventManagement.dto.UserUpdateDTO;
import tn.esprit.examen.EventManagement.entities.User;

import java.util.List;
import java.util.Optional;

public interface IServicesUser {
    User add(User user);
    User updateUser(Long id, UserUpdateDTO user);
    String deleteUser(Long id);
    User getUserById(Long id);
    List<User> getAllUsers();
    Optional<User> findByUsername(String username);
}
