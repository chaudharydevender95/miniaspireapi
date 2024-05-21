package com.example.miniaspireapi.service.impl;

import com.example.miniaspireapi.dao.Role;
import com.example.miniaspireapi.dao.User;
import com.example.miniaspireapi.dto.RegisterDto;
import com.example.miniaspireapi.repository.RoleRepository;
import com.example.miniaspireapi.repository.UserRepository;
import com.example.miniaspireapi.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Set;

/**
 * @author devenderchaudhary
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private RoleRepository roleRepository;

    public User saveUser(RegisterDto registerDto) {
        Set<Role> roles = null;
        if(!CollectionUtils.isEmpty(registerDto.getRoles()))
            roles = roleRepository.getRoleByNameIn(registerDto.getRoles());


        User user = User.builder()
            .username(registerDto.getUsername())
            .name(registerDto.getName())
            .email(registerDto.getEmail())
            .password(passwordEncoder.encode(registerDto.getPassword()))
            .roles(roles)
            .build();
        return userRepository.save(user);
    }

    public User getCurrentAuthenticatedUser() {
        if(Objects.isNull(SecurityContextHolder.getContext().getAuthentication()))
            return null;
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(name);
    }
}
