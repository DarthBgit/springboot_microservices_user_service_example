package com.app.userservice.service;

import com.app.userservice.entities.User;
import com.app.userservice.entities.dto.UserResponseDTO;
import com.app.userservice.mapper.UserMapper;
import com.app.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetAllUsers() {
        //Given
        User user = mock(User.class);
        User user2 = mock(User.class);
        List<User> mockUsers = List.of(user2, user);

        //When
        when(userRepository.findAll()).thenReturn(mockUsers);

        //Then
        var result = userService.getAllUsers();
        assert (result != null);
        assert (!result.isEmpty());
        assert (result.size() == 2);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetAllUsers_EmptyList() {
        //Given
        List<User> mockUsers = List.of();

        //When
        when(userRepository.findAll()).thenReturn(mockUsers);

        //Then
        try {
            userService.getAllUsers();
            assert false;
        } catch (Exception e) {
            assert e.getMessage().equals("No users found");
        }
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findUserById() {
        //Given
        Long userId = 1L;
        User user = mock(User.class);

        //When
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.entityToDto(user)).thenReturn(mock(UserResponseDTO.class));

        //Then
        var result = userService.getUserById(userId);
        assert (result != null);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void findUserById_NotFound() {
        //Given
        Long userId = 2L;

        //When
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        //Then
        try {
            userService.getUserById(userId);
            assert false;
        } catch (Exception e) {
            assert e.getMessage().equals("User not found with id: " + userId);
            verify(userRepository, times(1)).findById(userId);
        }
    }
}
