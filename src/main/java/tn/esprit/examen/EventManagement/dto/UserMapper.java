package tn.esprit.examen.EventManagement.dto;

import tn.esprit.examen.EventManagement.entities.User;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
                .genre(user.getGenre().name())
                .organizedEvents(EventMapper.toSummaryDTOList(user.getOrganizedEvents()))
                .reservations(user.getReservations().stream().map(ReservationMapper::toDTO).collect(Collectors.toList()))
                .profilePicture(user.getProfilePicture())
                .role(String.valueOf(user.getRole()))
                .build();
    }
}
