package control;

import model.Dictionary;
import model.Word;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

/**
 * Command line class, performs command line functions.
 */
public class DictionaryCommandLine {
    public static final String SHOW_ALL_WORD = "show all word";
    public static final String INSERT_FROM_COMMANDLINE = "insert from commandline";
    public static final String INSERT_FROM_FILE = "insert from file";
    public static final String LOOK_UP = "look up";
    public static final String REMOVE_WORD = "remove word";
    public static final String EXPORT_TO_FILE = "export to file";
    public static final String FIX_WORD = "fix word";
    public static final String ADD_WORD = "add word";

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

    public static Dictionary dictionarySearcher(Dictionary dictionary, String word_key) {
        Dictionary return_word_list = new Dictionary();
        if (!word_key.isEmpty()) {
            System.out.println("Searching for words that start with " + word_key);
            ArrayList<Word> word_list = dictionary.getWord_list();
            for (Word word : word_list) {
                String word_target = word.getWord_target();
                for (int i = 0; i < word_target.length(); i++) {
                    if (word_key.length() == i) {
                        return_word_list.getWord_list().add(word);
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
        String word_target;
        String word_explain;
        switch (COMMAND) {
            case SHOW_ALL_WORD: {
                showAllWords(dictionary);
                break;
            }
            case INSERT_FROM_FILE: {
                DictionaryManagement.insertFromFile(dictionary);
                break;
            }
            case LOOK_UP: System.out.println("tim tu khoa:");
                Scanner scanner = new Scanner(System.in);
                word_target = scanner.nextLine();
                word_explain = DictionaryManagement.dictionaryLookup(dictionary, word_target);
                if (!word_explain.equals("")) {
                    System.out.println("nghia cua "
                            + word_target + " la: "
                            + word_explain);
                }
            case ADD_WORD: {
                word_target = JOptionPane.showInputDialog(null, "từ thêm:");
                word_explain = JOptionPane.showInputDialog(null, "Y nghĩa");
                DictionaryManagement.addWord(dictionary, new Word(word_target, word_explain));
                break;
            }
            case REMOVE_WORD: {
                word_target = JOptionPane.showInputDialog(null, "chon từ xóa:");
                DictionaryManagement.removeWord(dictionary, word_target);
                break;
            }
            case FIX_WORD: {
                word_target = JOptionPane.showInputDialog(null, "chon từ sửa:");
                word_explain = JOptionPane.showInputDialog(null, "Y nghĩa mới:");
                DictionaryManagement.fixWord(dictionary, new Word(word_target, word_explain));
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
