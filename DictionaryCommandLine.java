package control;

import model.Dictionary;
import model.Word;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Command line class, performs command line functions.
 */
public class DictionaryCommandLine {
    private static final String SHOW_ALL_WORD = "show all word";
    private static final String INSERT_FROM_COMMANDLINE = "insert from commandline";
    private static final String INSERT_FROM_FILE = "insert from file";
    private static final String LOOK_UP = "look up";

    /**
     * Show all words in the dictionary
     */
    private void showAllWords(Dictionary dictionary) {
        ArrayList<Word> word_list = dictionary.getWord_list();
        if(word_list.isEmpty()) {
            System.out.println("Khong co tu trong tu dien.");
            return;
        }

        int count = 1;
        System.out.println("No\t|English\t|Vietnamese");
        for(Word i : word_list) {
            System.out.printf("%d\t|%s\t|%s\n", count, i.getWord_target(), i.getWord_explain());
            ++count;
        }
    }

    /**
     * Run basic commands.
     * Includes showAllWords() and InsertFromCommandLine();
     * @param COMMAND the command code
     */
    public void dictionaryBasic(String COMMAND, Dictionary dictionary) {
        switch (COMMAND) {
            case SHOW_ALL_WORD -> this.showAllWords(dictionary);
            case INSERT_FROM_COMMANDLINE -> DictionaryManagement.insertFromCommandline(dictionary);
            default -> System.out.println("Lenh khong hop le");
        }
    }

    /**
     *  additional command
     * @param COMMAND the command code
     * @param dictionary targeted dictinary
     */
    public void dictionaryAdvanced(String COMMAND, Dictionary dictionary) {
        switch (COMMAND) {
            case SHOW_ALL_WORD -> this.showAllWords(dictionary);
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
}