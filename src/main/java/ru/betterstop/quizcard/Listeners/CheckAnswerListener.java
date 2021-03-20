package ru.betterstop.quizcard.Listeners;

import ru.betterstop.quizcard.QuizCard;
import ru.betterstop.quizcard.QuizCardPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class CheckAnswerListener extends FormPlayListeners {

    public CheckAnswerListener(QuizCardPlayer play) {
        super(play);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        QuizCard card = play.getCurrentCard();
        JTextArea aTextArea = play.getAnswer();
        String answer = aTextArea.getText();
        JTextArea qTextArea = play.getQuestion();

        qTextArea.setText(card.getQuestion());

        if (card.getAnswer().equals(answer)) {
            card.setCountRight(1);
            aTextArea.setForeground(Color.GREEN);
            qTextArea.setText(qTextArea.getText() + "\nПравильно!");

        } else {
            card.setCountRight(-1);
            aTextArea.setForeground(Color.RED);
            qTextArea.setText(qTextArea.getText() + "\nОшибка!");
        }
    }
}