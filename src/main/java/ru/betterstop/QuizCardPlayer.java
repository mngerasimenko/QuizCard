package ru.betterstop;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class QuizCardPlayer {

    private JTextArea question;
    private JTextArea answer;
    private ArrayList<QuizCard> cardList;
    private QuizCard currentCard;
    private int currentCardId;
    private JFrame frame;
    private JButton prevButton;
    private JButton nextButton;
    private JButton showAnswerButton;

    private boolean isShowAnswer;

    public static void main(String[] args) {
        QuizCardPlayer reader = new QuizCardPlayer();
        reader.readCard();
    }

    public void readCard() {
        frame = new JFrame("Карточки с вопросами");
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);

        question = new JTextArea(6, 20);
        question.setFont(bigFont);
        question.setLineWrap(true);
        question.setEditable(false);

        JScrollPane qScroll = new JScrollPane(question);
        qScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        qScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        prevButton = new JButton("Предыдущая карточка");
        prevButton.setEnabled(false);
        prevButton.addActionListener(listener -> {
            if (currentCardId != 0) {

                showNextCard(-1);
                nextButton.setEnabled(true);
            } else {
                //question.setText("Больше нет карточек");
                //prevButton.setEnabled(false);
            }
        });

        nextButton = new JButton("Следующая карточка");
        nextButton.setEnabled(false);
        nextButton.addActionListener(listener -> {
           if (currentCardId < cardList.size()) {
               showNextCard(1);
               prevButton.setEnabled(true);
           } else {
               question.setText("Больше нет карточек");
           }
        });

        showAnswerButton = new JButton("Показать ответ");
        showAnswerButton.setEnabled(false);
        showAnswerButton.addActionListener(listener -> {
            if (currentCard != null) {
                question.setText("");
                question.setText(currentCard.getAnswer());
            }
        });

        mainPanel.add(qScroll);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(showAnswerButton);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        mainPanel.add(buttonPanel);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu= new JMenu("Файл");


        JMenuItem loadMenuItem = new JMenuItem("Загрузить набор карточек");
        loadMenuItem.addActionListener(listener -> {
            JFileChooser fileOpen = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Набор карточек (*.qac)", "qac");
            fileOpen.setFileFilter(filter);
            fileOpen.showOpenDialog(frame);
            loadFile(fileOpen.getSelectedFile());
        });
        fileMenu.add(loadMenuItem);

        JMenuItem builderCardItem = new JMenuItem("Создать набор Карточек");
        builderCardItem.addActionListener(listener -> {
            QuizCardBuilder builder = new QuizCardBuilder();
            builder.build();
        });
        fileMenu.add(builderCardItem);

        JMenuItem exitMenuItem = new JMenuItem("Выход");
        exitMenuItem.addActionListener(listener -> {
            frame.dispose();
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        //frame.getContentPane().add(BorderLayout.EAST,buttonPanel);
        frame.setSize(650, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void loadFile(File file) {
        cardList = new ArrayList<QuizCard>();
        try(FileInputStream fis = new FileInputStream(file); ObjectInputStream os = new ObjectInputStream(fis)) {
            while(fis.available() > 0) {
                    cardList.add((QuizCard) os.readObject());
            }
        } catch (EOFException e) {
            System.out.println("End of file");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        showNextCard(0);
        nextButton.setEnabled(true);
        showAnswerButton.setEnabled(true);
    }
    private void showNextCard(int direction) {
        if (currentCardId + direction >= 0 && currentCardId + direction < cardList.size()) {
            currentCardId += direction;
        }
        if (currentCardId == 0) prevButton.setEnabled(false);
        if (currentCardId == cardList.size() - 1) nextButton.setEnabled(false);

        currentCard = cardList.get(currentCardId);
        question.setText(currentCard.getQuestion());
    }
}
