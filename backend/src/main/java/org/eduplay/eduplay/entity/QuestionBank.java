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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(nullable = false)
    private String choiceA;

    @Column(nullable = false)
    private String choiceB;

    @Column(nullable = false)
    private String choiceC;

    @Column(nullable = false)
    private String choiceD;

    @Column(nullable = false)
    private String correctChoice;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    @Column(nullable = false)
    private Integer classLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AppLanguage language = AppLanguage.FRENCH;

    private String topicTag;

    private Integer qualityScore;

    @Column(nullable = false)
    @Builder.Default
    private Integer usageCount = 0;

    @Column(updatable = false)
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