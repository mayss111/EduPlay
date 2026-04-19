package org.eduplay.eduplay.repository;



import org.eduplay.eduplay.entity.Score;
import org.eduplay.eduplay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findByUserOrderByCreatedAtDesc(User user);

    @Query("SELECT u.username, u.avatarIndex, SUM(s.points) as total " +
            "FROM Score s JOIN s.user u " +
            "GROUP BY u.id, u.username, u.avatarIndex " +
            "ORDER BY total DESC")
    List<Object[]> findGlobalLeaderboard();

    @Query("SELECT u.username, u.avatarIndex, SUM(s.points) as total " +
            "FROM Score s JOIN s.user u " +
            "WHERE u.classLevel = :classLevel " +
            "GROUP BY u.id, u.username, u.avatarIndex " +
            "ORDER BY total DESC")
    List<Object[]> findLeaderboardByClass(Integer classLevel);
}