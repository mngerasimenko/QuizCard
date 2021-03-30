package ru.betterstop.quizcard;

import ru.betterstop.quizcard.Listeners.*;
import ru.betterstop.quizcard.settings.Setting;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class QuizCardPlayer extends CardWorker {

    private QuizCard currentCard;
    private int currentCardId;
    private JButton nextButton;
    private JButton showAnswerButton;
    private JButton checkAnswerButton;
    private boolean isOk = false;
    //private boolean finish = false;

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
        answer.addKeyListener(new AnswerKeyListener(this));

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

        loadMenuItem.addActionListener(new LoadCardListener(this));
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



    public void initForm() {
        nextButton.setEnabled(false);
        showAnswerButton.setEnabled(false);
        checkAnswerButton.setEnabled(false);
        if (cardList.size() != 0) {
            currentCardId = 0;
            currentCard = cardList.get(currentCardId);
            question.setText(currentCard.getQuestion());
            nextButton.setEnabled(true);
            showAnswerButton.setEnabled(true);
            checkAnswerButton.setEnabled(true);
        }
    }

    public boolean showCard() {
        if (cardList.size() == 0) {
            question.setText(Setting.FINISH);
            nextButton.setEnabled(false);
            showAnswerButton.setEnabled(false);
            checkAnswerButton.setEnabled(false);
            answer.setEditable(false);
            return false;
        }
        currentCardId = new Random().nextInt(cardList.size());
        currentCard = cardList.get(currentCardId);
        question.setText(currentCard.getQuestion());
        return true;
    }

 /*   public boolean showCard() {
        int count = cardList.size();
        finish = true;
        for(int i = 0; i < count; i++) {
            if (cardList.get(i).getCountRight() < Setting.COUNT_RIGHT) {
                finish = false;
                break;
            }
        }
        if (finish) {
            question.setText("Вы закончили все карточки в наборе!!!");
            nextButton.setEnabled(false);
            showAnswerButton.setEnabled(false);
            checkAnswerButton.setEnabled(false);
            answer.setEditable(false);
            return false;
        }

        int id = (currentCardId == cardList.size() - 1) ? 0 : currentCardId + 1;

        for(int i = id; i < count; i++) {
            if (cardList.get(i).getCountRight() < Setting.COUNT_RIGHT) {
                currentCardId = i;
                currentCard = cardList.get(i);
                break;
            }
            if (i == count - 1) {
                for (int j = 0; j < count; j++) {
                    if (cardList.get(j).getCountRight() < Setting.COUNT_RIGHT) {
                        currentCardId = j;
                        currentCard = cardList.get(j);
                        break;
                    }
                }
            }
        }
        question.setText(currentCard.getQuestion());
        return true;
    }
*/

    public JTextArea getAnswer() {
        return answer;
    }

    public QuizCard getCurrentCard() {
        return currentCard;
    }

    public int getCurrentCardId() {
        return currentCardId;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getCheckAnswerButton() {
        return checkAnswerButton;
    }

    public void setCurrentCard(QuizCard currentCard) {
        this.currentCard = currentCard;
    }

    public void setCurrentCardId(int currentCardId) {
        this.currentCardId = currentCardId;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

}
