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
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
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
        if (user.getAppLanguage() == null) {
            user.setAppLanguage(AppLanguage.FRENCH);
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
        if (request.getLanguage() != null && request.getLanguage() != user.getAppLanguage()) {
            user.setAppLanguage(request.getLanguage());
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
        log.info("Tentative d'inscription pour l'utilisateur: {}", request.getUsername());
        
        String normalizedUsername = request.getUsername() == null ? null : request.getUsername().trim();
        if (normalizedUsername == null || normalizedUsername.isBlank()) {
            throw new IllegalArgumentException("Username requis");
        }

        if (userRepository.existsByUsername(normalizedUsername)) {
            log.warn("Nom d'utilisateur déjà pris: {}", normalizedUsername);
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
                .appLanguage(request.getLanguage() == null ? AppLanguage.FRENCH : request.getLanguage())
                .classLevel(safeClassLevel)
                .avatarIndex(safeAvatarIndex)
                .totalXp(0)
                .streak(0)
                .createdAt(LocalDateTime.now())
                .build();

        try {
            log.info("Sauvegarde de l'utilisateur dans la base...");
            user = userRepository.save(user);
            log.info("Utilisateur sauvegardé avec succès. ID: {}", user.getId());
        } catch (Exception e) {
            log.error("Erreur critique lors de la sauvegarde: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur technique lors de la création du compte: " + e.getMessage());
        }

        log.info("Génération du token JWT...");
        String token = jwtUtil.generateToken(user.getUsername());
        return buildResponse(user, token);
    }

    private AuthResponse buildResponse(User user, String token) {
        Role safeRole = user.getRole() == null ? Role.STUDENT : user.getRole();
        AppLanguage safeLanguage = user.getAppLanguage() == null ? AppLanguage.FRENCH : user.getAppLanguage();
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