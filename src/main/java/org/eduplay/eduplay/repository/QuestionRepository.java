package org.eduplay.eduplay.repository;

import org.eduplay.eduplay.entity.Question;
import org.eduplay.eduplay.enums.Difficulty;
import org.eduplay.eduplay.enums.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByClassLevelAndSubjectAndDifficulty(
            Integer classLevel, Subject subject, Difficulty difficulty
    );
}