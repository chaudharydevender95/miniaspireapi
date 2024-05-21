package com.example.miniaspireapi.controller;

import com.example.miniaspireapi.dao.User;
import com.example.miniaspireapi.dto.RegisterDto;
import com.example.miniaspireapi.model.LoginResponse;
import com.example.miniaspireapi.model.LoginUserDto;
import com.example.miniaspireapi.service.impl.UserServiceImpl;
import com.example.miniaspireapi.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author devenderchaudhary
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(userService.saveUser(registerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginUserDto loginUserDto) throws Exception {
        Authentication authenticated = authenticate(loginUserDto.username(), loginUserDto.password());

        final UserDetails userDetails = (UserDetails) authenticated.getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token, jwtTokenUtil.getJwtExpiration()));
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw e;
        }
    }
}
