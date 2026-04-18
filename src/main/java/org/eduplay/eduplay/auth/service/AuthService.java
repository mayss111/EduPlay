package org.eduplay.eduplay.auth.service;


import org.eduplay.eduplay.auth.dto.*;
import org.eduplay.eduplay.entity.User;
import org.eduplay.eduplay.enums.Role;
import org.eduplay.eduplay.repository.UserRepository;
import org.eduplay.eduplay.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        String token = jwtUtil.generateToken(user.getUsername());
        return buildResponse(user, token);
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Ce pseudo est déjà pris !");
        }
        User user = User.builder()
                .firstName(request.getFirstName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .classLevel(request.getClassLevel())
                .avatarIndex(request.getAvatarIndex())
                .build();

        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return buildResponse(user, token);
    }

    private AuthResponse buildResponse(User user, String token) {
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .role(user.getRole().name())
                .classLevel(user.getClassLevel())
                .avatarIndex(user.getAvatarIndex())
                .totalXp(user.getTotalXp())
                .build();
    }
}