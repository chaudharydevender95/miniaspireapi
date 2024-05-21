package com.example.miniaspireapi.model;

/**
 * @author devenderchaudhary
 */
public record LoginResponse(String jwtToken, long expiresIn) { }