package org.eduplay.eduplay.repository;

import org.eduplay.eduplay.entity.UserQuestionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserQuestionHistoryRepository extends JpaRepository<UserQuestionHistory, Long> {

    /**
     * Trouve l'historique des questions pour un utilisateur donné
     */
    List<UserQuestionHistory> findByUserId(Long userId);

    /**
     * Vérifie si un utilisateur a déjà vu une question spécifique
     */
    Optional<UserQuestionHistory> findByUserIdAndQuestionId(Long userId, Long questionId);

    /**
     * Trouve les questions vues par un utilisateur depuis une date donnée
     */
    List<UserQuestionHistory> findByUserIdAndAnsweredAtAfter(Long userId, LocalDateTime date);

    /**
     * Compte le nombre de questions vues par un utilisateur
     */
    long countByUserId(Long userId);

    /**
     * Compte le nombre de réponses correctes par un utilisateur
     */
    long countByUserIdAndIsCorrectTrue(Long userId);

    /**
     * Trouve les questions vues dans une matière spécifique
     */
    @Query("SELECT uqh.questionId FROM UserQuestionHistory uqh " +
           "JOIN QuestionBank qb ON qb.id = uqh.questionId " +
           "WHERE uqh.userId = :userId AND qb.subject = :subject " +
           "AND uqh.answeredAt > :daysAgo")
    List<Long> findQuestionIdsBySubjectAndUser(
        @Param("userId") Long userId,
        @Param("subject") String subject,
        @Param("daysAgo") LocalDateTime daysAgo
    );

    /**
     * Supprime l'historique ancien (plus de 30 jours)
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM UserQuestionHistory uqh WHERE uqh.answeredAt < :cutoffDate")
    void deleteOldHistory(@Param("cutoffDate") LocalDateTime cutoffDate);

    /**
     * Trouve le taux de réussite par matière
     */
    @Query("SELECT COUNT(CASE WHEN uqh.isCorrect = true THEN 1 END) * 1.0 / COUNT(*) " +
           "FROM UserQuestionHistory uqh " +
           "JOIN QuestionBank qb ON qb.id = uqh.questionId " +
           "WHERE uqh.userId = :userId AND qb.subject = :subject")
    Double getSuccessRateBySubject(@Param("userId") Long userId, @Param("subject") String subject);
}