package com.app.userservice.entities.dto;

import com.app.userservice.entities.Role;
import com.app.userservice.entities.User;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private LocalDateTime createdAt;

    /**
     * Transform the response type User to UserResponseDTO.
     */
    public UserResponseDTO(User user) {
        this(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getCreatedAt());
    }
}