package tn.esprit.examen.EventManagement.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.EventManagement.dto.UserDTO;
import tn.esprit.examen.EventManagement.dto.UserMapper;
import tn.esprit.examen.EventManagement.dto.UserUpdateDTO;
import tn.esprit.examen.EventManagement.entities.User;
import tn.esprit.examen.EventManagement.services.IServicesUser;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class ClientRestController {
    private final IServicesUser userService;

    // ➕ Create
    @PostMapping("/add")
    public UserDTO addUser(@RequestBody User user) {
        return UserMapper.toDTO(userService.add(user));
    }

    // ✏️ Update
    @PutMapping("/update/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO user) {
        return UserMapper.toDTO(userService.updateUser(id, user));
    }

    // ❌ Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String message = userService.deleteUser(id);
        return ResponseEntity.ok(message);
    }

    // 🔍 Get One
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return UserMapper.toDTO(userService.getUserById(id));
    }

    // 📋 Get All
    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

}
