package ru.betterstop.quizcard;

import java.io.Serializable;

public class QuizCard implements Serializable {
    private String question;
    private String answer;
    private int countRight;

    public QuizCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.countRight = 0;

    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getQuestion() {
        return question;
    }
    public String getAnswer() {
        return answer;
    }

    public int getCountRight() {
        return countRight;
    }

    public void setCountRight(int right) {
        this.countRight = right;
    }
}
