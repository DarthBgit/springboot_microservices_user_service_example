package com.app.userservice.mapper;

import com.app.userservice.entities.User;
import com.app.userservice.entities.dto.UserRequestDTO;
import com.app.userservice.entities.dto.UserResponseDTO;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class UserMapper {

    public User dtoToEntity(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    public UserResponseDTO entityToDto(User user) {
        return new UserResponseDTO(user);
    }
}
