import control.DictionaryCommandLine;
import control.DictionaryManagement;
import model.Dictionary;

/**
 * Main program run.
 */
public class run {
    private static final String file_name = "dictionaries.txt";
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        DictionaryCommandLine.dictionaryBasic(DictionaryCommandLine.SHOW_ALL_WORD, dictionary);
    }
}
