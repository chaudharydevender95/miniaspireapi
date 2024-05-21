package com.example.miniaspireapi.service;

import com.example.miniaspireapi.dao.User;
import com.example.miniaspireapi.dto.RegisterDto;

/**
 * @author devenderchaudhary
 */
public interface UserService {
    User saveUser(RegisterDto registerDto);
    User getCurrentAuthenticatedUser();
}
