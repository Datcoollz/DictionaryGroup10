import control.DictionaryCommandLine;
import model.Dictionary;
import model.Word;
import view.DictionaryView;
//import view.DictionaryView;

public class run {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();

        DictionaryCommandLine.dictionaryAdvanced(DictionaryCommandLine.INSERT_FROM_FILE, dictionary);
        DictionaryCommandLine.dictionaryAdvanced(DictionaryCommandLine.SHOW_ALL_WORD, dictionary);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DictionaryView app = new DictionaryView(dictionary);
            }
        });
    }
}
