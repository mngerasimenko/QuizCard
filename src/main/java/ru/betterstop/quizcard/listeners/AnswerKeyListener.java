package ru.betterstop.quizcard.listeners;

import ru.betterstop.quizcard.QuizCardPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AnswerKeyListener implements KeyListener {
    private QuizCardPlayer play;

    public AnswerKeyListener(QuizCardPlayer play) {
        this.play = play;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        JTextField answer = play.getAnswer();
        if (c == '\n') {
                if (!play.isAnswerCorrect()) {
                    String str = answer.getText();
                    answer.setText(str);
                    CheckAnswerListener.checkAnswer(play);
                } else {
                    NextButtonListener.nextCard(play);
                }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!play.isAnswerCorrect()) {
            play.getQuestion().setText(play.getCurrentCard().getQuestion());
            play.getAnswer().setForeground(Color.BLACK);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
