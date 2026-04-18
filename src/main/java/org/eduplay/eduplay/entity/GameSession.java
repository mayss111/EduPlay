package org.eduplay.eduplay.entity;


import org.eduplay.eduplay.enums.Difficulty;
import org.eduplay.eduplay.enums.Subject;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column(nullable = false)
    private Integer classLevel;

    @Builder.Default
    private Integer score = 0;

    @Builder.Default
    private Integer correctAnswers = 0;

    @Builder.Default
    private Integer totalQuestions = 10;

    @Builder.Default
    private Integer xpEarned = 0;

    @Builder.Default
    private Boolean completed = false;

    @Column(updatable = false)
    @Builder.Default
    private LocalDateTime playedAt = LocalDateTime.now();
}