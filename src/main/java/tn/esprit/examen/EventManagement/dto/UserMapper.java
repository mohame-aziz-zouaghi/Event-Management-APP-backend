package tn.esprit.examen.EventManagement.dto;

import tn.esprit.examen.EventManagement.entities.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
                .genre(user.getGenre().name())
                .build();
    }
}
