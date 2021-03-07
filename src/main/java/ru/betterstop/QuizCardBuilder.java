package ru.betterstop;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class QuizCardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<QuizCard> cardList;
    private JFrame frame;

    public static void main(String[] args) {
        QuizCardBuilder builder = new QuizCardBuilder();
        builder.build();
    }

    public void build() {
        cardList = new ArrayList<QuizCard>();
        frame = new JFrame("Создание карточек с вопросами");
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

        JButton nextButton = new JButton("Следующая карточка");
        nextButton.addActionListener(listener -> {
            QuizCard card = new QuizCard(question.getText(), answer.getText());
            cardList.add(card);
            clearCard();
        });

        JLabel qLabel = new JLabel("Вопрос:");
        JLabel aLabel = new JLabel("Ответ:");

        mainPanel.add(qLabel);
        mainPanel.add(qScroll);
        mainPanel.add(aLabel);
        mainPanel.add(aScroll);
        mainPanel.add(nextButton);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem newMenuItem = new JMenuItem("Создать новый набор карточек");
        JMenuItem saveMenuItem = new JMenuItem("Сахранить");
        JMenuItem exitMenuItem = new JMenuItem("Выход");

        newMenuItem.addActionListener(listener -> {
            cardList.clear();
            clearCard();
        });
        saveMenuItem.addActionListener(listener -> {
            QuizCard quizCard = new QuizCard(question.getText(), answer.getText());
            cardList.add(quizCard);

            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        });
        exitMenuItem.addActionListener(listener -> {
            frame.dispose();
        });

        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(470, 600);
        frame.setVisible(true);
    }

    private void clearCard() {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }

    private void saveFile(File file) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            cardList.stream().forEach(x -> {
                try {
                    bw.write(x.getQuestion() + "/");
                    bw.write(x.getAnswer() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
