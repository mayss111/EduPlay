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

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long questionId;

    @Column(updatable = false)
    @Builder.Default
    private LocalDateTime answeredAt = LocalDateTime.now();

    private Boolean isCorrect;

    @Column(updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        answeredAt = LocalDateTime.now();
    }
}