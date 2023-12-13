package model.objects;

public class Tag {
    int tagID;
    int quizID;
    String tagName;

    public Tag(int quizID, String tagName, int tagID) {
        this.tagName = tagName;
        this.quizID = quizID;
        this.tagID = tagID;
    }

    public String getTagName() {
        return this.tagName;
    }

    public int getQuizID() {
        return this.quizID;
    }

    public int getTagID() {
        return this.tagID;
    }

}
