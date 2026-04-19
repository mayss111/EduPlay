package org.eduplay.eduplay.leaderboard.controller;



import org.eduplay.eduplay.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final ScoreRepository scoreRepository;

    @GetMapping("/global")
    public ResponseEntity<List<Map<String, Object>>> globalLeaderboard() {
        List<Object[]> results = scoreRepository.findGlobalLeaderboard();
        return ResponseEntity.ok(mapResults(results));
    }

    @GetMapping("/class/{classLevel}")
    public ResponseEntity<List<Map<String, Object>>> classLeaderboard(
            @PathVariable Integer classLevel) {
        List<Object[]> results = scoreRepository.findLeaderboardByClass(classLevel);
        return ResponseEntity.ok(mapResults(results));
    }

    private List<Map<String, Object>> mapResults(List<Object[]> results) {
        List<Map<String, Object>> leaderboard = new ArrayList<>();
        int rank = 1;
        for (Object[] row : results) {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("rank", rank++);
            entry.put("username", row[0]);
            entry.put("avatarIndex", row[1]);
            entry.put("totalPoints", row[2]);
            leaderboard.add(entry);
        }
        return leaderboard;
    }
}