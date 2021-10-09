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
    private static final String REMOVE_WORD = "remove word";
    private static final String EXPORT_TO_FILE = "export to file";
    private static final String FIX_WORD = "fix word";
    private static final String ADD_WORD = "add word";

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
            case SHOW_ALL_WORD: {
                this.showAllWords(dictionary);
                break;
            }
            case INSERT_FROM_COMMANDLINE: {
                DictionaryManagement.insertFromCommandline(dictionary);
                break;
            }
            default: {
                System.out.println("Lenh khong hop le");
                break;
            }
        }
    }

    /**
     *  additional command
     * @param COMMAND the command code
     * @param dictionary targeted dictinary
     */
    public void dictionaryAdvanced(String COMMAND, Dictionary dictionary) {
        switch (COMMAND) {
            case SHOW_ALL_WORD: {
                this.showAllWords(dictionary);
                break;
            }
            case INSERT_FROM_FILE: {
                DictionaryManagement.insertFromFile(dictionary);
                break;
            }
            case LOOK_UP: {
                DictionaryManagement.dictionaryLookup(dictionary);
                break;
            }
            case ADD_WORD: {
                DictionaryManagement.addWord(dictionary);
                break;
            }
            case REMOVE_WORD: {
                DictionaryManagement.removeWord(dictionary);
                break;
            }
            case FIX_WORD: {
                DictionaryManagement.fixWord(dictionary);
                break;
            }
            case EXPORT_TO_FILE: {
                DictionaryManagement.dictionaryExportTofile(dictionary);
                break;
            }
            default: {
                System.out.println("Lenh khong hop le");
                break;
            }
        }
    }
}
