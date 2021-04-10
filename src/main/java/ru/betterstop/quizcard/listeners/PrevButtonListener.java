package ru.betterstop.quizcard.listeners;

import ru.betterstop.quizcard.QuizCardPlayer;

import java.awt.event.ActionEvent;

public class PrevButtonListener extends FormPlayListeners {

    public PrevButtonListener(QuizCardPlayer play) {
        super(play);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (play.getCurrentCardId() != 0) {

            play.showCard();
            play.getNextButton().setEnabled(true);
        } else {
            //question.setText("Больше нет карточек");
            //prevButton.setEnabled(false);
        }
        play.getCheckAnswerButton().setEnabled(true);
        play.getAnswer().setText("");
    }
}
