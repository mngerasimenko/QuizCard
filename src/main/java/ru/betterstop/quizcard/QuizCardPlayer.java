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
        frame = new JFrame(Setting.FORM_PLAY_NAME);
        JPanel mainPanel = new JPanel();

        nextButton = createButton(Setting.BUTTON_NEXT, new NextButtonListener(this));
        checkAnswerButton = createButton(Setting.BUTTON_CHECK, new CheckAnswerListener(this));
        showAnswerButton = createButton(Setting.BUTTON_SHOW, new ShowAnswerListener(this));

        mainPanel.add(new JLabel(Setting.LABEL_QUESTION));
        mainPanel.add(initTextArea(question, 3,25));
        mainPanel.add(new JLabel(Setting.LABEL_ANSWER));
        initTextField(answer, 26);
        mainPanel.add(answer);
        question.setEditable(false);
        answer.addKeyListener(new AnswerKeyListener(this));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(checkAnswerButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(showAnswerButton);

        mainPanel.add(buttonPanel);
        frame.setJMenuBar(createMenu());
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(Setting.FORM_PLAY_WIDTH, Setting.FORM_PLAY_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private JMenuBar createMenu() {

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu= new JMenu(Setting.MENU_FILE);

        JMenuItem loadMenuItem = new JMenuItem(Setting.MENU_LOAD);
        loadMenuItem.addActionListener(new LoadCardListener(this));
        fileMenu.add(loadMenuItem);

        JMenuItem builderCardItem = new JMenuItem(Setting.MENU_CREATE);
        builderCardItem.addActionListener(listener -> {
            QuizCardBuilder builder = new QuizCardBuilder();
            builder.build();
        });
        fileMenu.add(builderCardItem);

        JMenuItem exitMenuItem = new JMenuItem(Setting.MENU_EXIT);
        exitMenuItem.addActionListener(listener -> {
            frame.dispose();
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
        return menuBar;
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
            answer.setEditable(true);
            answer.setText("");
            answer.requestFocus();
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
        if (cardList.size() == 1) {
            currentCardId = 0;
        } else {
            boolean nextCard = true;
            while (nextCard) {
                int randId = new Random().nextInt(cardList.size());
                if (randId != currentCardId) {
                    currentCardId = randId;
                    nextCard = false;
                }
            }
        }
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

    public JButton getShowAnswerButton() {
        return showAnswerButton;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

}
