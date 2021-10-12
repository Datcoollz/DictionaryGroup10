package control;

import model.Dictionary;
import model.Word;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Command line class, performs command line functions.
 */
public class DictionaryCommandLine {
    public static final String SHOW_ALL_WORD = "show all word";
    public static final String INSERT_FROM_COMMANDLINE = "insert from commandline";
    public static final String INSERT_FROM_FILE = "insert from file";
    public static final String LOOK_UP = "look up";

    /**
     * Show all words in the dictionary
     */
    private static void showAllWords(Dictionary dictionary) {
        ArrayList<Word> word_list = dictionary.getWord_list();
        if (word_list.isEmpty()) {
            System.out.println("Khong co tu nao thoa man.");
            return;
        }

        int count = 1;
        System.out.println("No\t|English\t|Vietnamese");
        for (Word i : word_list) {
            System.out.printf("%d\t|%s\t|%s\n", count, i.getWord_target(), i.getWord_explain());
            ++count;
        }
    }

    /**
     * Run basic commands.
     * Includes showAllWords() and InsertFromCommandLine();
     *
     * @param COMMAND the command code
     */
    public static void dictionaryBasic(String COMMAND, Dictionary dictionary) {
        switch (COMMAND) {
            case SHOW_ALL_WORD -> showAllWords(dictionary);
            case INSERT_FROM_COMMANDLINE -> DictionaryManagement.insertFromCommandline(dictionary);
            default -> System.out.println("Lenh khong hop le");
        }
    }

    /**
     * Additional command.
     *
     * @param COMMAND    the command code
     * @param dictionary targeted dictinary
     */
    public static void dictionaryAdvanced(String COMMAND, Dictionary dictionary) {
        switch (COMMAND) {
            case SHOW_ALL_WORD -> showAllWords(dictionary);
            case INSERT_FROM_FILE -> DictionaryManagement.insertFromFile(dictionary);
            case INSERT_FROM_COMMANDLINE -> DictionaryManagement.insertFromCommandline(dictionary);
            case LOOK_UP -> {
                System.out.println("tim tu khoa:");
                Scanner scanner = new Scanner(System.in);
                String word_target = scanner.nextLine();
                String word_definition = DictionaryManagement.dictionaryLookup(dictionary, word_target);
                if (!word_definition.equals("")) {
                    System.out.println("nghia cua "
                            + word_target + " la: "
                            + word_definition);
                }
            }
            default -> System.out.println("Lenh khong hop le");
        }
    }

    /**
     * Search for words that contain a string.
     *
     * @param dictionary the dictionary for searching
     * @param word_key   the input string for searching
     * @return a dictionary containing the words
     */
    public static Dictionary dictionarySearcher(Dictionary dictionary, String word_key) {
        Dictionary return_word_list = new Dictionary();
        if (!word_key.isEmpty()) {
            System.out.println("Searching for words that start with " + word_key);
            ArrayList<Word> word_list = dictionary.getWord_list();
            for (Word word : word_list) {
                String word_target = word.getWord_target();
                for (int i = 0; i < word_target.length(); i++) {
                    if (word_key.length() == i) {
                        return_word_list.addWord(word);
                        break;
                    }
                    if(word_target.charAt(i) != word_key.charAt(i)) {
                        break;
                    }
                }
            }
        }
        return return_word_list;
    }
}
