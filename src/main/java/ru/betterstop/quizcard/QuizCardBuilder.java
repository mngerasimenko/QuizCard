package ru.betterstop.quizcard;

import ru.betterstop.quizcard.Listeners.LoadCardListener;
import ru.betterstop.quizcard.Listeners.SaveCardListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
        cardList = new ArrayList<QuizCard>();
        frame = new JFrame("Создание карточек с вопросами");
        frame.getContentPane().add(BorderLayout.CENTER, createPanel());
        frame.setJMenuBar(createMenu());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(470, 600);
        frame.setVisible(true);
    }

    private JPanel createPanel() {
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);
        question = new JTextArea(6, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);

        JScrollPane qScroll = new JScrollPane(question);
        qScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        qScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        answer = new JTextArea(6, 20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(bigFont);

        JScrollPane aScroll = new JScrollPane(answer);
        aScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        initButton();

        JLabel qLabel = new JLabel("Вопрос:");
        JLabel aLabel = new JLabel("Ответ:");

        mainPanel.add(qLabel);
        mainPanel.add(qScroll);
        mainPanel.add(aLabel);
        mainPanel.add(aScroll);
        mainPanel.add(newButton);
        mainPanel.add(saveButton);
        mainPanel.add(prevButton);
        mainPanel.add(nextButton);

        return mainPanel;
    }

    private void initButton() {
        newButton = new JButton("Новая карточка");
        saveButton = new JButton("Сохранить карточку");
        prevButton = new JButton("Предыдущая карточка");
        nextButton = new JButton("Следующая карточка");
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
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem newMenuItem = new JMenuItem("Создать новый набор карточек");
        JMenuItem loadMenuItem = new JMenuItem("Загрузить набор карточек");
        JMenuItem saveMenuItem = new JMenuItem("Сахранить набор карточек");
        JMenuItem exitMenuItem = new JMenuItem("Закрыть");

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
