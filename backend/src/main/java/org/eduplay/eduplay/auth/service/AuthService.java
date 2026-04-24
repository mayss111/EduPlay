package org.eduplay.eduplay.auth.service;


import org.eduplay.eduplay.auth.dto.*;
import org.eduplay.eduplay.entity.User;
import org.eduplay.eduplay.enums.AppLanguage;
import org.eduplay.eduplay.enums.Role;
import org.eduplay.eduplay.repository.UserRepository;
import org.eduplay.eduplay.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Identifiants invalides");
        }

        boolean requiresUpdate = false;
        if (user.getRole() == null) {
            user.setRole(Role.STUDENT);
            requiresUpdate = true;
        }
        if (user.getLanguage() == null) {
            user.setLanguage(AppLanguage.FRENCH);
            requiresUpdate = true;
        }
        if (user.getTotalXp() == null) {
            user.setTotalXp(0);
            requiresUpdate = true;
        }
        if (user.getStreak() == null) {
            user.setStreak(0);
            requiresUpdate = true;
        }
        if (request.getLanguage() != null && request.getLanguage() != user.getLanguage()) {
            user.setLanguage(request.getLanguage());
            requiresUpdate = true;
        }
        if (requiresUpdate) {
            userRepository.save(user);
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return buildResponse(user, token);
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String normalizedUsername = request.getUsername() == null ? null : request.getUsername().trim();
        if (normalizedUsername == null || normalizedUsername.isBlank()) {
            throw new IllegalArgumentException("Username requis");
        }

        if (userRepository.existsByUsername(normalizedUsername)) {
            throw new IllegalArgumentException("Ce pseudo est déjà pris !");
        }

        int safeClassLevel = request.getClassLevel() == null ? 1 : request.getClassLevel();
        int safeAvatarIndex = request.getAvatarIndex() == null ? 0 : request.getAvatarIndex();
        String safeFirstName = request.getFirstName() == null ? "" : request.getFirstName().trim();

        User user = User.builder()
                .firstName(safeFirstName)
                .username(normalizedUsername)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .language(request.getLanguage() == null ? AppLanguage.FRENCH : request.getLanguage())
                .classLevel(safeClassLevel)
                .avatarIndex(safeAvatarIndex)
                .build();

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Inscription invalide. Vérifie le pseudo et les champs requis.");
        }
        String token = jwtUtil.generateToken(user.getUsername());
        return buildResponse(user, token);
    }

    private AuthResponse buildResponse(User user, String token) {
        Role safeRole = user.getRole() == null ? Role.STUDENT : user.getRole();
        AppLanguage safeLanguage = user.getLanguage() == null ? AppLanguage.FRENCH : user.getLanguage();
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .role(safeRole.name())
                .language(safeLanguage.name())
                .classLevel(user.getClassLevel())
                .avatarIndex(user.getAvatarIndex())
                .totalXp(user.getTotalXp() == null ? 0 : user.getTotalXp())
                .build();
    }
}