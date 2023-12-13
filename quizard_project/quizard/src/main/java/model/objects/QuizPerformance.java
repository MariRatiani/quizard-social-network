package model.objects;

import java.time.Duration;
import java.time.LocalDateTime;

public class QuizPerformance {
    private int userId, quizId, score, possibleMaxScore;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public QuizPerformance(int userId, int quizId, int score, int possibleMaxScore, LocalDateTime startTime, LocalDateTime endTime) {
        this.userId = userId;
        this.score = score;
        this.startTime = startTime;
        this.endTime = endTime;
        this.quizId = quizId;
        this.possibleMaxScore = possibleMaxScore;
    }

    public int getUserId() {
        return userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getScore() {
        return score;
    }

    public int getPossibleMaxScore() {
        return possibleMaxScore;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getDurationInSecs() {
        Duration duration = Duration.between(startTime, endTime);
        return (int) duration.getSeconds();
    }
}