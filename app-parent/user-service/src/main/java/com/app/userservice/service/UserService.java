package com.app.userservice.service;

import com.app.userservice.entities.User;
import com.app.userservice.entities.dto.UserRequestDTO;
import com.app.userservice.entities.dto.UserResponseDTO;
import com.app.userservice.exception.UserNotFoundException;
import com.app.userservice.repository.UserRepository;
import com.app.userservice.exception.UserAlreadyExistsException;
import com.app.userservice.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new UserNotFoundException("No users found");
        }
        return users.stream()
                .map(userMapper::entityToDto)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return userMapper.entityToDto(user);
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        validateUserDoesNotExist(userRequestDTO, -1L);
        User user = userMapper.dtoToEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        userRepository.save(user);
        return userMapper.entityToDto(user);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        validateUserDoesNotExist(userRequestDTO, id);

        existingUser.setUsername(userRequestDTO.getUsername());
        existingUser.setEmail(userRequestDTO.getEmail());
        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }
        existingUser.setRole(userRequestDTO.getRole());

        userRepository.save(existingUser);
        return userMapper.entityToDto(existingUser);
    }

    public void removeUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    private void validateUserDoesNotExist(UserRequestDTO dto, Long currentUserId) {
        if (userRepository.existsByUsernameAndIdNot(dto.getUsername(), currentUserId)) {
            throw new UserAlreadyExistsException("Username already exists: " + dto.getUsername());
        }
        if (userRepository.existsByEmailAndIdNot(dto.getEmail(), currentUserId)) {
            throw new UserAlreadyExistsException("Email already exists: " + dto.getEmail());
        }
    }
}

