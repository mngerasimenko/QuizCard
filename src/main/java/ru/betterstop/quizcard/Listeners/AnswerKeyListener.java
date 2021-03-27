package ru.betterstop.quizcard.Listeners;

import ru.betterstop.quizcard.QuizCardPlayer;

import javax.swing.*;
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
        JTextArea answer = play.getAnswer();
        if (c == '\n') {

                if (!play.isOk()) {
                    String str = answer.getText();
                    str = str.substring(0, str.length() - 1);
                    answer.setText(str);
                    CheckAnswerListener.checkAnswer(play);
                } else {
                    NextButtonListener.nextCard(play);
                }

        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        char c = e.getKeyChar();
//        if (c == '\n') {
//
//            CheckAnswerListener.checkAnswer(play);
//            String str = play.getAnswer().getText();
//            str = str.substring(0,str.length() - 1);
//            play.getAnswer().setText(str);
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
