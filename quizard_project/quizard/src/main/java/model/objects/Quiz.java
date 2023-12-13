package model.objects;

import java.sql.Date;
import java.time.LocalDateTime;

public class Quiz {
    int quizId;
    int creatorID;
    String quizName;
    String description;
    String category;
    boolean randomOrder;
    boolean multiplePages;
    int maxPoints;
    int amountTaken;
    boolean immediateCorrection;
    LocalDateTime creationTime;
    int timeLimitInSeconds;


    public Quiz(int quizId, String quizName, String description, String category,
                int creatorID, boolean randomOrder, boolean multiplePages, boolean immediateCorrection, boolean practiceMode,
                LocalDateTime creationTime, int timeLimitInSeconds, int amount_taken , int maxPoints) {
        this.creatorID = creatorID;
        this.description = description;
        this.category = category;
        this.immediateCorrection = immediateCorrection;
        this.randomOrder = randomOrder;
        this.multiplePages = multiplePages;
        this.quizName = quizName;
        this.quizId = quizId;
        this.creationTime = creationTime;
        this.timeLimitInSeconds = timeLimitInSeconds;
        this.amountTaken = amount_taken;
        this.maxPoints = maxPoints;
    }

    public int getQuizId() {
        return quizId;
    }

    public String getName() {
        return quizName;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public boolean isRandomOrder() {
        return randomOrder;
    }

    public boolean displayOnMultiplePages() {
        return multiplePages;
    }

    public LocalDateTime getCreationTime() {return creationTime;}

    public int getMaxPoints() {
        return maxPoints;
    }

    public boolean shouldCorrectImmediatly() {
        return immediateCorrection;
    }

    public int getAmountTaken() {
        return amountTaken;
    }
    public int getTimeLimit(){
        return timeLimitInSeconds;
    }

}