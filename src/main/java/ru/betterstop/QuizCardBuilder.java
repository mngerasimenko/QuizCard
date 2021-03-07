package ru.betterstop;

import javax.swing.*;
import java.awt.*;
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
        frame = new JFrame("Создание карточек с вопросами");
        JPanel mainPanel = new JPanel();

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem newMenuItem = new JMenuItem("Создать");
        JMenuItem saveMenuItem = new JMenuItem("Сахранить");
        JMenuItem exitMenuItem = new JMenuItem("Выход");

        newMenuItem.addActionListener(listner -> {
            System.out.println("New");
        });
        saveMenuItem.addActionListener(listner -> {
            System.out.println("Save");
        });
        exitMenuItem.addActionListener(listner -> {
            frame.dispose();
        });

        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500, 600);
        frame.setVisible(true);
    }
}
