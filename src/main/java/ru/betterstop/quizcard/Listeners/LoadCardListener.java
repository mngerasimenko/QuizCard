package ru.betterstop.quizcard.Listeners;

import ru.betterstop.quizcard.CardWorker;
import ru.betterstop.quizcard.QuizCard;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class LoadCardListener implements ActionListener {

    CardWorker cardWorker;

    public LoadCardListener(CardWorker cardWorker) {
        this.cardWorker = cardWorker;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFrame frame = cardWorker.getFrame();
        JFileChooser fileOpen = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Набор карточек (*.qac)", "qac");
        fileOpen.setFileFilter(filter);
        if (fileOpen.showOpenDialog(frame) == 0) {
            loadFile(fileOpen.getSelectedFile());
        }
    }

    private void loadFile(File file) {
        ArrayList<QuizCard> cardList = cardWorker.getCardList();
        if (!cardList.isEmpty()) cardList.clear();
        try (FileInputStream fis = new FileInputStream(file); ObjectInputStream os = new ObjectInputStream(fis)) {
            while (fis.available() > 0) {
                QuizCard card = (QuizCard) os.readObject();
                cardList.add(card);
            }
        } catch (EOFException e) {
            System.out.println("End of file");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        cardWorker.initForm();
    }
}
