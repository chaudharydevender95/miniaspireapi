package com.example.miniaspireapi.dto;

import lombok.Data;

import java.util.List;

/**
 * @author devenderchaudhary
 */
@Data
public class RegisterDto {
    private String name;
    private String email;
    private String username;
    private String password;
    private List<String> roles;
}
