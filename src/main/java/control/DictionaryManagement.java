package control;

import model.Dictionary;
import model.Word;

import javax.swing.*;
import java.io.File;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class DictionaryManagement {

    /**
     * Insert word from commandline
     */
    public static void insertFromCommandline(Dictionary dictionary) {
        Scanner input = new Scanner(System.in);
        int word_num = input.nextInt();
        input.nextLine();
        String input_word;
        String input_definition;
        for (int i = 0; i < word_num; i++) {
            input_word = input.nextLine();
            input_definition = input.nextLine();
            dictionary.getWordList().add(new Word(input_word, input_definition));
        }
        input.close();
    }

    /**
     * Insert word from File
     */
    public static void insertFromFile(Dictionary dictionary) {
        try {
            File file = new File("dictionaries.txt");
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String data = input.nextLine();
                String[] ss = data.split("\t");
                dictionary.getWordList().add(new Word(ss[0], ss[1]));
            }

        } catch (FileNotFoundException e) {
            System.out.println(("No file found"));
        }
    }

    /**
     * Look for a word's definition.
     *
     * @param dictionary  input dictionary
     * @param word_target word_target to search for definition
     * @return word_explain of the word_target
     */
    public static String dictionaryLookup(Dictionary dictionary, String word_target) {
        for (Word i : dictionary.getWordList()) {
            if (Objects.equals(i.getWordTarget(), word_target)) {
                return i.getWordExplain();
            }
        }
        JOptionPane.showMessageDialog(null, "Word not found.");
        return "";
    }


    /**
     * Add a new word to the list
     */
    public static void addWord(Dictionary dictionary, Word word) {
        //Search for closest word
        int index = Collections.binarySearch(dictionary.getWordList(), word,
                (o1, o2) -> o1.getWordTarget().compareToIgnoreCase(o2.getWordTarget()));
        if (index < 0) {
            index = (index * -1) - 1;
        }
        //Check if dictionary is empty
        if (dictionary.getWordList().isEmpty()) {
            dictionary.getWordList().add(word);
        } else {
            //Check if word already exists in dictionary
            if (word.getWordTarget().equals(dictionary.getWordList().get(index).getWordTarget())) {
                JOptionPane.showMessageDialog(null, "Word already exist");
            } else {
                dictionary.getWordList().add(index, word);
                JOptionPane.showMessageDialog(null, "Word added");
            }
        }
    }

    /**
     * remove a word.
     */
    public static void removeWord(Dictionary dictionary, Word word) {
        dictionary.getWordList().removeIf(i -> Objects.equals(i.getWordTarget(), word.getWordTarget()));
    }

    /**
     * change a word's definition;
     */
    public static void fixWord(Dictionary dictionary, Word word) {
        int index = Collections.binarySearch(dictionary.getWordList(), word,
                (o1, o2) -> o1.getWordTarget().compareToIgnoreCase(o2.getWordTarget()));
        if (index < 0) {
            index = (index * -1) - 1;
        }
        if (dictionary.getWordList().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Word list is empty");
        } else {
            if (dictionary.getWordList().size() <= index
                    || !word.getWordTarget().equals(dictionary.getWordList().get(index).getWordTarget())) {
                JOptionPane.showMessageDialog(null, "Word does not exist");
            } else {
                dictionary.getWordList().get(index).setWordExplain(word.getWordExplain());
                JOptionPane.showMessageDialog(null, "Word edited successfully");
            }
        }
    }

    /**
     * Output dictionary to a file.
     */
    public static void dictionaryExportToFile(Dictionary dictionary) {
        File file = new File("dictionaries.txt");
        try {
            PrintWriter output = new PrintWriter(file);
            for (Word i : dictionary.getWordList()) {
                output.println(i.getWordTarget() + "\t" + i.getWordExplain());
            }
            output.close();
        } catch (FileNotFoundException e) {
            System.out.println("No file found");
        }
    }
}
