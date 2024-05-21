package com.example.miniaspireapi.service;

import com.example.miniaspireapi.dao.User;
import com.example.miniaspireapi.dto.RegisterDto;
import com.example.miniaspireapi.repository.RoleRepository;
import com.example.miniaspireapi.repository.UserRepository;
import com.example.miniaspireapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author devenderchaudhary
 */
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser_Success() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("username");
        registerDto.setName("name");
        registerDto.setEmail("email@example.com");
        registerDto.setPassword("password");
        registerDto.setRoles(new ArrayList<>());

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setRoles(new HashSet<>());

        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.saveUser(registerDto);

        assertEquals(user, result);
    }

    @Test
    public void testSaveUser_DatabaseError() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("username");
        registerDto.setName("name");
        registerDto.setEmail("email@example.com");
        registerDto.setPassword("password");
        registerDto.setRoles(new ArrayList<>());

        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userService.saveUser(registerDto));
    }

    @Test
    public void testSaveUser_NullDto() {
        assertThrows(NullPointerException.class, () -> userService.saveUser(null));
    }

    @Test
    public void testGetCurrentAuthenticatedUser_UserExists() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("username");
        SecurityContextHolder.setContext(securityContext);

        User user = new User();
        when(userRepository.findByUsername("username")).thenReturn(user);

        User result = userService.getCurrentAuthenticatedUser();

        assertEquals(user, result);
    }

    @Test
    public void testGetCurrentAuthenticatedUser_UserDoesNotExist() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("username");
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername("username")).thenReturn(null);

        User result = userService.getCurrentAuthenticatedUser();

        assertNull(result);
    }

    @Test
    public void testGetCurrentAuthenticatedUser_NoAuthentication() {
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        User result = userService.getCurrentAuthenticatedUser();

        assertNull(result);
    }
}