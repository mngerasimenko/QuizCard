package ru.betterstop.quizcard.listeners;

import ru.betterstop.quizcard.QuizCardPlayer;
import java.awt.event.ActionEvent;

public class ShowAnswerListener extends FormPlayListeners {

    public ShowAnswerListener(QuizCardPlayer play) {
        super(play);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play.getCurrentCard() != null) {
                play.getQuestion().setText("");
                play.getQuestion().setText(play.getCurrentCard().getAnswer());
                play.getCurrentCard().setCountRight(-1);
            }
        play.getCheckAnswerButton().setEnabled(false);
        play.getAnswer().setEnabled(false);
        play.getAnswer().setText("");
        play.getNextButton().requestFocus();
    }
}
