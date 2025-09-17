package tn.esprit.examen.EventManagement.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.EventManagement.dto.LoginRequestDTO;
import tn.esprit.examen.EventManagement.dto.RegisterRequestDTO;
import tn.esprit.examen.EventManagement.services.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        try {
            var user = authService.register(request);
            return ResponseEntity.ok("User registered: " + user.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        try {
            String token = authService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(Map.of(
                    "username", request.getUsername(),
                    "token", token
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
