package tn.esprit.examen.EventManagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import tn.esprit.examen.EventManagement.entities.Event;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String genre;
    private List<EventSummaryDTO> organizedEvents;
    private List<EventSummaryDTO> participatingEvents;
}
