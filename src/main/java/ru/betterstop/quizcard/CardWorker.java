package ru.betterstop.quizcard;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class CardWorker {

    protected ArrayList<QuizCard> cardList = new ArrayList<>();
    protected JFrame frame;
    protected JTextArea question;
    protected JTextArea answer;

    protected JButton createButton(String buttonName, ActionListener listener) {
        JButton button = new JButton(buttonName);
        button.setEnabled(false);
        button.addActionListener(listener);
        return button;
    }

    public abstract void initForm();

    public JFrame getFrame() {
        return frame;
    }

    public ArrayList<QuizCard> getCardList() {
        return cardList;
    }

    public JTextArea getQuestion() {
        return question;
    }

    public JTextArea getAnswer() {
        return answer;
    }
}
