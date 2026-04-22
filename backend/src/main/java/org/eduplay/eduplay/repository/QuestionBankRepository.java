package org.eduplay.eduplay.repository;

import org.eduplay.eduplay.entity.QuestionBank;
import org.eduplay.eduplay.enums.Difficulty;
import org.eduplay.eduplay.enums.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuestionBankRepository extends JpaRepository<QuestionBank, Long> {

    /**
     * Trouve les questions les moins utilisées que l'utilisateur n'a pas vues récemment
     */
    @Query("SELECT q FROM QuestionBank q WHERE q.subject = :subject " +
           "AND q.classLevel = :classLevel AND q.difficulty = :difficulty " +
           "AND q.id NOT IN (SELECT uqh.questionId FROM UserQuestionHistory uqh " +
           "WHERE uqh.userId = :userId AND uqh.answeredAt > :daysAgo) " +
           "ORDER BY q.usageCount ASC, FUNCTION('RANDOM')")
    List<QuestionBank> findLeastUsedNotRecentlySeen(
        @Param("userId") Long userId,
        @Param("subject") Subject subject,
        @Param("classLevel") Integer classLevel,
        @Param("difficulty") Difficulty difficulty,
        @Param("daysAgo") LocalDateTime daysAgo
    );

    /**
     * Trouve les questions les moins utilisées (sans filtre utilisateur)
     */
    @Query("SELECT q FROM QuestionBank q WHERE q.subject = :subject " +
           "AND q.classLevel = :classLevel AND q.difficulty = :difficulty " +
           "ORDER BY q.usageCount ASC, FUNCTION('RANDOM')")
    List<QuestionBank> findLeastUsed(
        @Param("subject") Subject subject,
        @Param("classLevel") Integer classLevel,
        @Param("difficulty") Difficulty difficulty
    );

    /**
     * Compte le nombre total de questions pour une combinaison donnée
     */
    long countBySubjectAndClassLevelAndDifficulty(
        Subject subject,
        Integer classLevel,
        Difficulty difficulty
    );

    /**
     * Trouve des questions aléatoires pour une combinaison donnée
     */
    @Query(value = "SELECT * FROM question_bank q WHERE q.subject = :subject " +
           "AND q.class_level = :classLevel AND q.difficulty = :difficulty " +
           "ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<QuestionBank> findRandom(
        @Param("subject") Subject subject,
        @Param("classLevel") Integer classLevel,
        @Param("difficulty") Difficulty difficulty,
        @Param("limit") int limit
    );

    /**
     * Trouve les questions par matière et niveau de classe
     */
    List<QuestionBank> findBySubjectAndClassLevel(Subject subject, Integer classLevel);

    /**
     * Trouve les questions par niveau de classe et difficulté
     */
    List<QuestionBank> findByClassLevelAndDifficulty(Integer classLevel, Difficulty difficulty);

    /**
     * Trouve les questions déjà vues par l'utilisateur récemment
     */
    @Query("SELECT uqh.questionId FROM UserQuestionHistory uqh " +
           "WHERE uqh.userId = :userId AND uqh.answeredAt > :daysAgo")
    List<Long> findRecentlySeenQuestionIds(
        @Param("userId") Long userId,
        @Param("daysAgo") LocalDateTime daysAgo
    );
}