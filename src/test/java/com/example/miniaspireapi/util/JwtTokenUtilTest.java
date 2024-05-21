package com.example.miniaspireapi.util;

import com.example.miniaspireapi.config.CustomUserDetails;
import com.example.miniaspireapi.dao.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author devenderchaudhary
 */
class JwtTokenUtilTest {

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jwtTokenUtil, "secretKey", "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b");
        ReflectionTestUtils.setField(jwtTokenUtil,"jwtExpiration", 36000);
    }

    @Test
    public void testExtractUsername_ValidToken() {
        String token = jwtTokenUtil.generateToken(userDetails);
        String username = jwtTokenUtil.extractUsername(token);

        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    public void testExtractUsername_InvalidToken() {
        assertThrows(RuntimeException.class, () -> jwtTokenUtil.extractUsername("invalidToken"));
    }

    @Test
    public void testExtractExpiration_ValidToken() {
        String token = jwtTokenUtil.generateToken(userDetails);
        Date expiration = jwtTokenUtil.extractExpiration(token);

        assertNotNull(expiration);
    }

    @Test
    public void testExtractExpiration_InvalidToken() {
        assertThrows(RuntimeException.class, () -> jwtTokenUtil.extractExpiration("invalidToken"));
    }

    @Test
    public void testGenerateToken_ValidUserDetails() {

        String token = jwtTokenUtil.generateToken(getUserDetails());

        assertNotNull(token);
    }

    @Test
    public void testValidateToken_ValidTokenAndUserDetails() {
        String token = jwtTokenUtil.generateToken(getUserDetails());
        boolean isValid = jwtTokenUtil.validateToken(token, getUserDetails());

        assertTrue(isValid);
    }

    @Test
    public void testValidateToken_ValidTokenAndInvalidUserDetails() {
        String token = jwtTokenUtil.generateToken(getUserDetails());
        UserDetails invalidUserDetails = new CustomUserDetails(new User());
        boolean isValid = jwtTokenUtil.validateToken(token, invalidUserDetails);

        assertFalse(isValid);
    }

    private UserDetails getUserDetails() {
        User user = User.builder().username("username").build();
        return new CustomUserDetails(user);
    }

}