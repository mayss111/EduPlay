package org.eduplay.eduplay.auth.dto;


import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String username;
    private String firstName;
    private String role;
    private Integer classLevel;
    private Integer avatarIndex;
    private Integer totalXp;
}