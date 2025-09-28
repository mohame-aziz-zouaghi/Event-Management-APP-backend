package tn.esprit.examen.EventManagement.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.examen.EventManagement.dto.RegisterRequestDTO;
import tn.esprit.examen.EventManagement.dto.UserDTO;
import tn.esprit.examen.EventManagement.dto.UserMapper;
import tn.esprit.examen.EventManagement.dto.UserUpdateDTO;
import tn.esprit.examen.EventManagement.entities.User;
import tn.esprit.examen.EventManagement.services.IServicesUser;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class ClientRestController {
    private final IServicesUser userService;

    // ➕ Create
    @PostMapping("/add")
    public UserDTO addUser(
            @RequestPart("user") RegisterRequestDTO userDto,
            @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture
    ) throws IOException {
        return UserMapper.toDTO(userService.add(userDto, profilePicture));
    }

    // ✏️ Update
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @RequestPart("user") UserUpdateDTO userUpdateDTO,
            @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture
    ) throws IOException {
        User updatedUser = userService.updateUser(id, userUpdateDTO, profilePicture);
        return ResponseEntity.ok(UserMapper.toDTO(updatedUser));
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
