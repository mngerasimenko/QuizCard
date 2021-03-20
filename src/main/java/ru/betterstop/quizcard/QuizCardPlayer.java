package ru.betterstop.quizcard;

import ru.betterstop.quizcard.Listeners.CheckAnswerListener;
import ru.betterstop.quizcard.Listeners.NextButtonListener;
import ru.betterstop.quizcard.Listeners.PrevButtonListener;
import ru.betterstop.quizcard.Listeners.ShowAnswerListener;
import ru.betterstop.quizcard.setting.Setting;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class QuizCardPlayer {

    private JTextArea question;
    private JTextArea answer;
    private ArrayList<QuizCard> cardList;
    private QuizCard currentCard;
    private int currentCardId;
    private JFrame frame;
    //private JButton prevButton;
    private JButton nextButton;
    private JButton showAnswerButton;
    private JButton checkAnswerButton;


    public static void main(String[] args) {
        QuizCardPlayer play = new QuizCardPlayer();
        play.checkingAnswers();
    }

    public void checkingAnswers() {
        frame = new JFrame("Карточки с вопросами");
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);

        question = new JTextArea(3, 25);
        question.setFont(bigFont);
        question.setLineWrap(true);
        question.setEditable(false);

        JScrollPane qScroll = new JScrollPane(question);
        qScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        qScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        answer = new JTextArea(3, 25);
        answer.setFont(bigFont);
        answer.setLineWrap(true);

        JScrollPane aScroll = new JScrollPane(answer);
        aScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        aScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //prevButton = createButton("Предыдущая карточка", new PrevButtonListener(this));
        nextButton = createButton("Следующая карточка", new NextButtonListener(this));
        checkAnswerButton = createButton("Проверить ответ", new CheckAnswerListener(this));
        showAnswerButton = createButton("Показать ответ", new ShowAnswerListener(this));

        mainPanel.add(qScroll);
        mainPanel.add(aScroll);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(checkAnswerButton);
        //buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(showAnswerButton);

        //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
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

    private JButton createButton(String buttonName, ActionListener listener) {
        JButton button = new JButton(buttonName);
        button.setEnabled(false);
        button.addActionListener(listener);
        return button;
    }

    private void loadFile(File file) {
        cardList = new ArrayList<QuizCard>();
        try(FileInputStream fis = new FileInputStream(file); ObjectInputStream os = new ObjectInputStream(fis)) {
            while(fis.available() > 0) {
                    QuizCard card = (QuizCard) os.readObject();
                    cardList.add(card);
            }
        } catch (EOFException e) {
            System.out.println("End of file");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        currentCardId = 0;
        currentCard = cardList.get(currentCardId);
        question.setText(currentCard.getQuestion());
        nextButton.setEnabled(true);
        showAnswerButton.setEnabled(true);
        checkAnswerButton.setEnabled(true);
    }

    public void showCard() {
        int count = cardList.size();
        boolean finish = true;
        for(int i = 0; i < count; i++) {
            if (cardList.get(i).getCountRight() < Setting.COUNT_RIGHT) finish = false;
        }
        if (finish) {
            question.setText("Вы закончили все карточки в наборе!!!");
            nextButton.setEnabled(false);
            showAnswerButton.setEnabled(false);
            checkAnswerButton.setEnabled(false);
            return;
        }

        int id = (currentCardId == cardList.size() - 1) ? 0 : currentCardId + 1;

        for(int i = id; i < count; i++) {
            if (cardList.get(i).getCountRight() < Setting.COUNT_RIGHT) {
                currentCardId = i;
                currentCard = cardList.get(i);
                break;
            }
        }
        question.setText(currentCard.getQuestion());
    }

    public JTextArea getQuestion() {
        return question;
    }

    public JTextArea getAnswer() {
        return answer;
    }

    public QuizCard getCurrentCard() {
        return currentCard;
    }

    public int getCurrentCardId() {
        return currentCardId;
    }

    public ArrayList<QuizCard> getCardList() {
        return cardList;
    }

//    public JButton getPrevButton() {
//        return prevButton;
//    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getCheckAnswerButton() {
        return checkAnswerButton;
    }

    public void setQuestion(JTextArea question) {
        this.question = question;
    }

    public void setCurrentCard(QuizCard currentCard) {
        this.currentCard = currentCard;
    }

    public void setCurrentCardId(int currentCardId) {
        this.currentCardId = currentCardId;
    }
}
