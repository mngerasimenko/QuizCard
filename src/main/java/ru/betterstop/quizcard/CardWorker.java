package ru.betterstop.quizcard;

import ru.betterstop.quizcard.settings.Setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class CardWorker {

    protected ArrayList<QuizCard> cardList = new ArrayList<>();
    protected JFrame frame;
    protected JTextArea question = new JTextArea();
    protected JTextArea answer = new JTextArea();

    protected JButton createButton(String buttonName, ActionListener listener) {
        JButton button = new JButton(buttonName);
        button.setEnabled(false);
        button.addActionListener(listener);
        return button;
    }

    protected JScrollPane initTextArea(JTextArea textArea, int row, int col) {
        Font bigFont = new Font(Setting.FONT_NAME, Font.BOLD, 24);
        textArea.setRows(row);
        textArea.setColumns(col);
        textArea.setFont(bigFont);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
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
