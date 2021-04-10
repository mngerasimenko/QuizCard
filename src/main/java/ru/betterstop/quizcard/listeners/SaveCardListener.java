package ru.betterstop.quizcard.listeners;

import ru.betterstop.quizcard.QuizCard;
import ru.betterstop.quizcard.settings.Setting;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveCardListener implements ActionListener {

    ArrayList<QuizCard> cardList;
    JFrame frame;

    public SaveCardListener(JFrame frame, ArrayList<QuizCard> cardList) {
        this.cardList = cardList;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileSave = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(Setting.FILE_FILTER_NAME, Setting.FILE_FILTER_EXT);
        fileSave.setFileFilter(filter);
        if (fileSave.showSaveDialog(frame) == 0) {
            String fileName = fileSave.getSelectedFile().getAbsolutePath();
            if (!fileName.endsWith("." + Setting.FILE_FILTER_EXT)) {
                fileName += "." + Setting.FILE_FILTER_EXT;
            }
            saveFile(new File(fileName));
        }
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
