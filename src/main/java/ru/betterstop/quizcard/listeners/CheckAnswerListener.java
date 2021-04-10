package ru.betterstop.quizcard.listeners;

import ru.betterstop.quizcard.QuizCard;
import ru.betterstop.quizcard.QuizCardPlayer;
import ru.betterstop.quizcard.settings.Setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class CheckAnswerListener extends FormPlayListeners {

    public CheckAnswerListener(QuizCardPlayer play) {
        super(play);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkAnswer(play);
    }

    public static void checkAnswer(QuizCardPlayer play) {
        QuizCard card = play.getCurrentCard();
        JTextField textField = play.getAnswer();
        String answer = textField.getText();
        answer = answer.toLowerCase();
        answer = removeWhiteSpace(answer);
        play.getAnswer().setText(answer);
        JTextArea qTextArea = play.getQuestion();
        qTextArea.setText(card.getQuestion());
        if (card.getAnswer().equals(answer)) {
            card.setCountRight(card.getCountRight() + 1);
            textField.setForeground(Color.GREEN);
            qTextArea.setText(qTextArea.getText() + "\n" + Setting.CORRECT_ANSWER);
            play.getCheckAnswerButton().setEnabled(false);
            play.setOk(true);
            if (card.getCountRight() == Setting.COUNT_RIGHT) {
                play.getCardList().remove(play.getCurrentCardId());
            }
            play.getShowAnswerButton().setEnabled(false);
        } else {
            card.setCountRight(-1);
            textField.setForeground(Color.RED);
            qTextArea.setText(qTextArea.getText() + "\n" + Setting.ERROR_ANSWER);
        }
    }

    private static String removeWhiteSpace(String inputString) {
        String[] str = inputString.split(" ");
        inputString = "";
        for (String s: str) {
            s.replaceAll("\\s+","");
            if (!s.equals("")) {
                inputString += s + " ";
            }
        }
        return inputString.trim();
    }
}
