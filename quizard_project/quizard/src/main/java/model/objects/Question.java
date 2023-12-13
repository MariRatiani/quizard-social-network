package model.objects;

import model.enums.QuestionType;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private int quiz_id;
    private int question_id;
    private String questionTxt;
    private QuestionType questionType;
    private List<String>answers;

    public Question(int quiz_id,int question_id, String questionBody, QuestionType questionType, List<String>answers) {
        this.quiz_id = quiz_id;
        this.question_id = question_id;
        this.questionTxt = questionBody;
        this.questionType = questionType;
        this.answers = answers;
    }
    public List<String>getAnswers(){
        return answers;
    }
    public int getQuestionId() {
        return question_id;
    }

    public int getQuizId() {
        return quiz_id;
    }

    public String getQuestionTxt() {
        return questionTxt;
    }

    public void setQuestionTxt(String questionTxt) {
        this.questionTxt = questionTxt;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
}
