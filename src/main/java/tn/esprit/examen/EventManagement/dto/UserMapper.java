package tn.esprit.examen.EventManagement.dto;

import tn.esprit.examen.EventManagement.entities.User;
import tn.esprit.examen.EventManagement.mappers.EventMapper;

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
                .organizedEvents(EventMapper.toSummaryDTOList(user.getOrganizedEvents()))
                .participatingEvents(EventMapper.toSummaryDTOList(user.getParticipatingEvents()))
                .build();
    }
}
