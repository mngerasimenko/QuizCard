package ru.betterstop.quizcard.Listeners;

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
            }
        play.getCheckAnswerButton().setEnabled(false);
        play.getAnswer().setText("");
    }
}