package tn.esprit.examen.EventManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.examen.EventManagement.entities.Event;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Date dateOfBirth;
    private String password;
    private String genre;
    private String role = "USER"; // optional, defaults to USER if null
}
