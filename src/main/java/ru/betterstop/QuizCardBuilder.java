package ru.betterstop;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class QuizCardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<QuizCard> cardList;
    private JFrame frame;
    private static int itemCard;

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

        JButton newButton = new JButton("Новая карточка");
        JButton saveButton = new JButton("Сохранить карточку");
        JButton prevButton = new JButton("Предыдущая карточка");
        JButton nextButton = new JButton("Следующая карточка");
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
            if (cardList.size() == itemCard + 1 ) nextButton.setEnabled(false);
        });

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

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem newMenuItem = new JMenuItem("Создать новый набор карточек");
        JMenuItem saveMenuItem = new JMenuItem("Сахранить");
        JMenuItem exitMenuItem = new JMenuItem("Закрыть");

        newMenuItem.addActionListener(listener -> {
            cardList.clear();
            clearCard();
        });
        saveMenuItem.addActionListener(listener -> {
            //QuizCard quizCard = new QuizCard(question.getText(), answer.getText());
            JFileChooser fileSave = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Набор карточек (*.qac)", "qac");
            fileSave.setFileFilter(filter);
            fileSave.showSaveDialog(frame);
            String fileName = fileSave.getSelectedFile().getAbsolutePath();
            if (!fileName.endsWith(".qac")) {
                fileName += ".qac";
            }
            saveFile(new File(fileName));
        });
        exitMenuItem.addActionListener(listener -> {
            frame.setVisible(false);
            frame.dispose();
        });

        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(470, 600);
        frame.setVisible(true);
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

    private void saveFile(File file) {
        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file))) {
            cardList.forEach(obj -> {
                try {
                    os.writeObject(obj);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println("Ошибка сохранения");
            e.printStackTrace();
        }

    }
}
