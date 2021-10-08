package control;

import model.Dictionary;
import model.Word;

/**
 * Command line class, performs command line functions.
 */
public class DictionaryCommandLine {
    public static final int SHOW_ALL_WORD = 914;
    public static final int INSERT_FROM_COMMANDLINE = 627;

    /**
     * Show all words in the dictionary
     */
    public static void showAllWords(Dictionary dictionary) {
        if(dictionary.getWord_list().isEmpty()) {
            System.out.println("Khong co tu trong tu dien.");
            return;
        }

        int count = 1;
        System.out.println("No|English|Vietnamese");
        for(Word i : dictionary.getWord_list()) {
            System.out.printf("%d|%s|%s\n", count, i.getWord_target(), i.getWord_explain());
            ++count;
        }
    }

    /**
     * Run basic commands.
     * Includes showAllWords() and InsertFromCommandLine();
     * @param COMMAND the command code
     */
    public static void dictionaryBasic(int COMMAND, Dictionary dictionary) {
        switch (COMMAND) {
            case SHOW_ALL_WORD -> DictionaryCommandLine.showAllWords(dictionary);
            case INSERT_FROM_COMMANDLINE -> DictionaryManagement.insertFromCommandline(dictionary);
            default -> System.out.println("Lenh khong hop le");
        }
    }
}
