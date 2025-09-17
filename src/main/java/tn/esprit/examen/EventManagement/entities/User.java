package tn.esprit.examen.EventManagement.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String firstName;

    String lastName;

    @Column(unique = true, nullable = false)
    String username;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    Genre genre;

    @Enumerated(EnumType.STRING)
    Role role; // e.g. ADMIN, USER, ORGANIZER
}
