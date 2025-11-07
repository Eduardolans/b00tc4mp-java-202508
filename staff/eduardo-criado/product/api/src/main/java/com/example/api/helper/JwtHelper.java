package com.example.api.helper;

import static com.example.api.util.JwtUtil.validateToken;

public class JwtHelper {

    public static String generateTokenWithUserId(String userId) {
        return com.example.api.util.JwtUtil.generateToken(userId);
    }

    public static String validateTokenAndGetUserId(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null; // Retornar null cuando NO es válido
        }

        String token = authHeader.substring(7);
        return validateToken(token);
    }

}
