package ru.betterstop.quizcard.Listeners;

import ru.betterstop.quizcard.QuizCardPlayer;
import java.awt.event.ActionEvent;

public class NextButtonListener extends FormPlayListeners {

    public NextButtonListener(QuizCardPlayer play) {
        super(play);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play.getCurrentCardId() < play.getCardList().size()) {
            play.showNextCard(1);
            play.getPrevButton().setEnabled(true);
        } else {
            play.getQuestion().setText("Больше нет карточек");
        }
        play.getCheckAnswerButton().setEnabled(true);
        play.getAnswer().setText("");
    }
}
