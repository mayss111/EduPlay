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
        try {
            log.info("--- DEBUT INSCRIPTION ---");
            log.info("Pseudo: {}, Langue: {}, Classe: {}", request.getUsername(), request.getLanguage(), request.getClassLevel());
            
            String normalizedUsername = request.getUsername() == null ? null : request.getUsername().trim();
            if (normalizedUsername == null || normalizedUsername.isBlank()) {
                throw new IllegalArgumentException("Username requis");
            }

            if (userRepository.existsByUsername(normalizedUsername)) {
                log.warn("Username existe déjà: {}", normalizedUsername);
                throw new IllegalArgumentException("Ce pseudo est déjà pris !");
            }

            User user = new User();
            user.setFirstName(request.getFirstName() == null ? "" : request.getFirstName().trim());
            user.setUsername(normalizedUsername);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(Role.STUDENT);
            user.setAppLanguage(request.getLanguage() == null ? AppLanguage.FRENCH : request.getLanguage());
            user.setClassLevel(request.getClassLevel() == null ? 1 : request.getClassLevel());
            user.setAvatarIndex(request.getAvatarIndex() == null ? 0 : request.getAvatarIndex());
            user.setTotalXp(0);
            user.setStreak(0);

            log.info("Tentative de sauvegarde DB...");
            user = userRepository.save(user);
            log.info("Sauvegarde DB OK. ID: {}", user.getId());

            log.info("Génération du token...");
            String token = jwtUtil.generateToken(user.getUsername());
            
            log.info("--- INSCRIPTION REUSSIE ---");
            return buildResponse(user, token);
            
        } catch (Exception e) {
            log.error("[FATAL REGISTRATION ERROR] : " + e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'inscription : " + e.getMessage());
        }
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