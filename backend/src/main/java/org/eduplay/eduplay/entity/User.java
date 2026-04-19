package org.eduplay.eduplay.entity;

import org.eduplay.eduplay.enums.Role;
import org.eduplay.eduplay.enums.AppLanguage;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AppLanguage language = AppLanguage.FRENCH;

    private Integer classLevel;

    private Integer avatarIndex;

    @Builder.Default
    private Integer totalXp = 0;

    @Builder.Default
    private Integer streak = 0;

    @Column(updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}