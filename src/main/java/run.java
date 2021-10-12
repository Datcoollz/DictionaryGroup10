import control.DictionaryManagement;
import control.DictionaryCommandLine;
import model.Word;
import model.Dictionary;
import view.DictionaryApplication;

import javax.swing.*;
import java.util.Scanner;

public class run {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();

        //DictionaryCommandLine.dictionaryAdvanced(DictionaryCommandLine.INSERT_FROM_COMMANDLINE, dictionary);
        DictionaryCommandLine.dictionaryAdvanced(DictionaryCommandLine.INSERT_FROM_FILE, dictionary);

        DictionaryCommandLine.dictionaryAdvanced(DictionaryCommandLine.SHOW_ALL_WORD, dictionary);

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DictionaryApplication app = new DictionaryApplication();
                app.updateWordList(dictionary);
            }
        });

    }
}
