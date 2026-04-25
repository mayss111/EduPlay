package org.eduplay.eduplay.entity;

import org.eduplay.eduplay.enums.Role;
import org.eduplay.eduplay.enums.AppLanguage;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "app_language")
    @Builder.Default
    private AppLanguage appLanguage = AppLanguage.FRENCH;

    @Column(name = "class_level")
    private Integer classLevel;

    @Column(name = "avatar_index")
    private Integer avatarIndex;

    @Column(name = "total_xp")
    @Builder.Default
    private Integer totalXp = 0;

    @Column(name = "streak")
    @Builder.Default
    private Integer streak = 0;

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}