package ru.betterstop.quizcard.Listeners;

import ru.betterstop.quizcard.QuizCard;
import ru.betterstop.quizcard.QuizCardPlayer;

import java.awt.*;
import java.awt.event.ActionEvent;

public class NextButtonListener extends FormPlayListeners {

    public NextButtonListener(QuizCardPlayer play) {
        super(play);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        play.getAnswer().setForeground(Color.BLACK);
        play.showCard();
        play.getCheckAnswerButton().setEnabled(true);
        play.getAnswer().setText("");
    }
}
