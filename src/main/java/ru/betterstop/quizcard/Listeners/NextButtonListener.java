package ru.betterstop.quizcard.Listeners;

import ru.betterstop.quizcard.QuizCardPlayer;

import java.awt.*;
import java.awt.event.ActionEvent;

public class NextButtonListener extends FormPlayListeners {

    public NextButtonListener(QuizCardPlayer play) {
        super(play);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nextCard(play);
    }

    public static void nextCard(QuizCardPlayer play) {
        play.getAnswer().setForeground(Color.BLACK);
        play.getShowAnswerButton().setEnabled(true);
        if (play.showCard()) {
            play.getCheckAnswerButton().setEnabled(true);
        } else {
            play.getShowAnswerButton().setEnabled(false);
        }
        play.getAnswer().setText("");
        play.getAnswer().setEnabled(true);
        play.getAnswer().requestFocus();
        play.setOk(false);
    }
}
