package control;

import model.Dictionary;
import model.Word;

import java.util.ArrayList;

/**
 * Command line class, performs command line functions.
 */
public class DictionaryCommandLine {
    public static final String SEARCH_KEY_WORD = "search words from key";
    public static final String SHOW_ALL_WORD = "show all word";
    public static final String INSERT_FROM_COMMANDLINE = "insert from commandline";
    public static final String INSERT_FROM_FILE = "insert from file";
    public static final String REMOVE_WORD = "remove word";
    public static final String EXPORT_TO_FILE = "export to file";
    public static final String FIX_WORD = "fix word";
    public static final String ADD_WORD = "add word";

    /**
     * Show all words in the dictionary to commandline
     */
    private static void showAllWords(ArrayList<Word> list) {
        if (list.isEmpty()) {
            System.out.println("Khong co tu trong tu dien.");
            return;
        }
        int count = 1;
        System.out.println("No\t|English\t|Vietnamese");
        for (Word i : list) {
            System.out.printf("%d\t|%s\t|%s\n", count, i.getWordTarget(), i.getWordExplain());
            ++count;
        }
    }

    /**
     * Additional command, general commands that doesn't require a word.
     * Includes showAllWord(), insertFromFile() and exportToFile().
     */
    public static void dictionaryAdvanced(String COMMAND, Dictionary dictionary) {
        switch (COMMAND) {
            case SHOW_ALL_WORD: {
                showAllWords(dictionary.searchAll());
                break;
            }
            case INSERT_FROM_COMMANDLINE: {
                DictionaryManagement.insertFromCommandline(dictionary);
            }
            case INSERT_FROM_FILE: {
                DictionaryManagement.insertFromFile(dictionary);
                break;
            }
            case EXPORT_TO_FILE: {
                DictionaryManagement.dictionaryExportToFile(dictionary);
                break;
            }
            default: {
                System.out.println("Lenh khong hop le");
                break;
            }
        }
    }

    /**
     * Additional commands, these are commands that require a word.
     * Includes addWord(), removeWord(), fixWord(), dictionarySearcher and dictionaryLookup()
     */
    public static ArrayList<Word> dictionaryAdvanced(String COMMAND, Dictionary dictionary, Word word) {
        switch (COMMAND) {
            case ADD_WORD: {
                DictionaryManagement.addWord(dictionary, word);
                break;
            }
            case REMOVE_WORD: {
                DictionaryManagement.removeWord(dictionary, word);
                break;
            }
            case FIX_WORD: {
                DictionaryManagement.fixWord(dictionary, word);
                break;
            }
            case SEARCH_KEY_WORD: {
                return DictionaryManagement.searchWord(dictionary, word);
            }
            default: {
                System.out.println("Lenh khong hop le");
                break;
            }
        }
        return null;
    }
}
