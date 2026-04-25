package org.eduplay.eduplay.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_question_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQuestionHistory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "answered_at", updatable = false)
    @Builder.Default
    private LocalDateTime answeredAt = LocalDateTime.now();

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        answeredAt = LocalDateTime.now();
    }
}