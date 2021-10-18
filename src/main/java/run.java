import control.DictionaryCommandLine;
import model.Dictionary;
import view.DictionaryView;

public class run {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        dictionary.insert("abE");
        dictionary.insert("aDE");
        dictionary.insert("DCwweaXE");
        dictionary.insert("CASEX");
        dictionary.insert("bsAAx");
        dictionary.insert("bRScc");
        dictionary.insert("a");
        dictionary.insert("ABG");

        System.out.println(dictionary.searchPrefix("a"));
        System.out.println(dictionary.searchPrefix("ab"));
        System.out.println(dictionary.searchPrefix("b"));
        System.out.println(dictionary.searchAll());

//        DictionaryCommandLine.dictionaryAdvanced(DictionaryCommandLine.INSERT_FROM_FILE, dictionary);
//        DictionaryCommandLine.dictionaryAdvanced(DictionaryCommandLine.SHOW_ALL_WORD, dictionary);
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                DictionaryView app = new DictionaryView(dictionary);
//                app.updateWordList(dictionary);
//            }
//        });
    }
}
