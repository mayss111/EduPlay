package org.eduplay.eduplay.entity;

import org.eduplay.eduplay.enums.AppLanguage;
import org.eduplay.eduplay.enums.Difficulty;
import org.eduplay.eduplay.enums.Subject;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "question_bank")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionBank implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "choice_a", nullable = false)
    private String choiceA;

    @Column(name = "choice_b", nullable = false)
    private String choiceB;

    @Column(name = "choice_c", nullable = false)
    private String choiceC;

    @Column(name = "choice_d", nullable = false)
    private String choiceD;

    @Column(name = "correct_choice", nullable = false)
    private String correctChoice;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    @Column(name = "class_level", nullable = false)
    private Integer classLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "app_language", nullable = false)
    @Builder.Default
    private AppLanguage appLanguage = AppLanguage.FRENCH;

    @Column(name = "topic_tag")
    private String topicTag;

    @Column(name = "quality_score")
    private Integer qualityScore;

    @Column(name = "usage_count", nullable = false)
    @Builder.Default
    private Integer usageCount = 0;

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void incrementUsage() {
        this.usageCount = this.usageCount + 1;
    }
}