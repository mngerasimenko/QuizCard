package ru.betterstop.quizcard;

import ru.betterstop.quizcard.Listeners.LoadCardListener;
import ru.betterstop.quizcard.Listeners.SaveCardListener;
import ru.betterstop.quizcard.settings.Setting;

import javax.swing.*;
import java.awt.*;

public class QuizCardBuilder extends CardWorker {

    private static int itemCard;
    private JButton nextButton;
    private JButton prevButton;
    private JButton newButton;
    private JButton saveButton;

    public static void main(String[] args) {
        QuizCardBuilder builder = new QuizCardBuilder();
        builder.build();
    }

    public void build() {
        frame = new JFrame(Setting.FORM_BUILDER_NAME);
        frame.getContentPane().add(BorderLayout.CENTER, createPanel());
        frame.setJMenuBar(createMenu());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(Setting.FORM_BUILD_WIDTH, Setting.FORM_BUILD_HEIGHT);
        frame.setVisible(true);
    }

    private JPanel createPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.add(new JLabel(Setting.LABEL_QUESTION));
        mainPanel.add(initTextArea(question, 6, 20));
        mainPanel.add(new JLabel(Setting.LABEL_ANSWER));
        //mainPanel.add(initTextArea(answer, 6, 20));
        initTextField(answer, 21);
        mainPanel.add(answer);
        initButton();
        mainPanel.add(newButton);
        mainPanel.add(saveButton);
        mainPanel.add(prevButton);
        mainPanel.add(nextButton);
        return mainPanel;
    }

    private void initButton() {
        newButton = new JButton(Setting.BUTTON_NEW);
        saveButton = new JButton(Setting.BUTTON_SAVE);
        prevButton = new JButton(Setting.BUTTON_PREV);
        nextButton = new JButton(Setting.BUTTON_NEXT);
        prevButton.setEnabled(false);
        nextButton.setEnabled(false);

        newButton.addActionListener(listener -> {
            clearCard();
            itemCard = cardList.size();
            nextButton.setEnabled(false);
        });
        saveButton.addActionListener(listener -> {
            if (cardList.size() == itemCard) {
                QuizCard card = new QuizCard(question.getText(), answer.getText());
                cardList.add(card);
                clearCard();
                prevButton.setEnabled(true);
                itemCard = cardList.size();
            } else {
                cardList.get(itemCard).setAnswer(answer.getText());
                cardList.get(itemCard).setQuestion(question.getText());
            }
        });
        prevButton.addActionListener(listener -> {
            itemCard--;
            QuizCard card = cardList.get(itemCard);
            showCard(card);
            nextButton.setEnabled(true);
            if (itemCard == 0) prevButton.setEnabled(false);

        });
        nextButton.addActionListener(listener -> {
            cardList.get(itemCard).setAnswer(answer.getText());
            cardList.get(itemCard).setQuestion(question.getText());
            QuizCard card = cardList.get(++itemCard);
            showCard(card);
            prevButton.setEnabled(true);
            if (cardList.size() == itemCard + 1) nextButton.setEnabled(false);
        });
    }

    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(Setting.MENU_FILE);
        JMenuItem newMenuItem = new JMenuItem(Setting.MENU_CREATE);
        JMenuItem loadMenuItem = new JMenuItem(Setting.MENU_LOAD);
        JMenuItem saveMenuItem = new JMenuItem(Setting.MENU_SAVE);
        JMenuItem exitMenuItem = new JMenuItem(Setting.MENU_EXIT);

        newMenuItem.addActionListener(listener -> {
            cardList.clear();
            initForm();
        });
        loadMenuItem.addActionListener(new LoadCardListener(this));
        saveMenuItem.addActionListener(new SaveCardListener(frame, cardList));
        exitMenuItem.addActionListener(listener -> {
            frame.setVisible(false);
            frame.dispose();
        });

        fileMenu.add(newMenuItem);
        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        return menuBar;
    }

    private void showCard(QuizCard card) {
        question.setText(card.getQuestion());
        answer.setText(card.getAnswer());
    }

    private void clearCard() {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }

    public void initForm() {
        nextButton.setEnabled(false);
        clearCard();
        if (cardList.size() != 0) {
            question.setText(cardList.get(0).getQuestion());
            answer.setText(cardList.get(0).getAnswer());
            nextButton.setEnabled(true);
        }
        itemCard = 0;
        prevButton.setEnabled(false);
    }

}
