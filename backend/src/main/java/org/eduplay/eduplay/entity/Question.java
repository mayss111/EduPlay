package org.eduplay.eduplay.entity;


import org.eduplay.eduplay.enums.AppLanguage;
import org.eduplay.eduplay.enums.Difficulty;
import org.eduplay.eduplay.enums.Subject;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String questionText;

    private String choiceA;

    private String choiceB;

    private String choiceC;

    private String choiceD;

    private String correctChoice;

    private String explanation;

    private Subject subject;

    private Integer classLevel;

    private Difficulty difficulty;

    private AppLanguage appLanguage;

    private Integer qualityScore;

    private String topicTag;

    private String generationProfile;
}