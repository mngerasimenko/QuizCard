package ru.betterstop.quizcard.Listeners;

import ru.betterstop.quizcard.QuizCardPlayer;
import java.awt.event.ActionListener;

public abstract class FormPlayListeners implements ActionListener {
    protected QuizCardPlayer play;

    public FormPlayListeners(QuizCardPlayer play) {
        this.play = play;
    }
}
