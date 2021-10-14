import control.DictionaryCommandLine;
import model.Dictionary;
import view.DictionaryView;

import java.util.Scanner;

public class run {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        DictionaryCommandLine.dictionaryAdvanced(DictionaryCommandLine.INSERT_FROM_FILE, dictionary);
        DictionaryCommandLine.dictionaryAdvanced(DictionaryCommandLine.SHOW_ALL_WORD, dictionary);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DictionaryView app = new DictionaryView(dictionary);
                app.updateWordList(dictionary);
            }
        });
    }
}
