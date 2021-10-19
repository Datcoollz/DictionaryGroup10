package control;

import model.Dictionary;
import model.Word;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class DictionaryManagement {
    public static final String FILE_PATH = "dictionaries.txt";

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
            DictionaryManagement.addWord(dictionary, new Word(input_word, input_definition));
        }
        input.close();
    }

    /**
     * Insert word from File
     */
    public static void insertFromFile(Dictionary dictionary) {
        try {
            Scanner input = new Scanner(new FileInputStream(FILE_PATH));
            while (input.hasNextLine()) {
                String data = input.nextLine();
                String[] ss = data.split("\t");
                if (ss.length > 1) {
                    dictionary.insert(new Word(ss[0], ss[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(("No file found"));
        }
    }

    /**
     * Search whether a word is in a dictionary.
     * No use right now.
     *
     * @return the Word found
     */
    public static Word dictionaryLookup(Dictionary dictionary, Word word) {
        return dictionary.search(word);
    }

    /**
     * Call the searchPrefix method in trie.
     * Search for words with input prefix.
     *
     * @param dictionary input dictionary
     * @param word       prefix, takes wordTarget
     * @return Array list of Word
     */
    public static ArrayList<Word> searchWord(Dictionary dictionary, Word word) {
        return dictionary.searchPrefix(word);
    }

    /**
     * Add a word to the dictionary.
     *
     * @param dictionary the dictionary
     * @param word       word to add
     */
    public static void addWord(Dictionary dictionary, Word word) {
        dictionary.insert(word);
    }

    /**
     * Remove a word from a dictionary.
     *
     * @param dictionary the dictionary
     * @param word       word to delete
     */
    public static void removeWord(Dictionary dictionary, Word word) {
        dictionary.delete(word);
    }

    /**
     * change a word's definition.
     */
    public static void fixWord(Dictionary dictionary, Word word) {
        dictionary.editWord(word);
    }

    /**
     * Output dictionary to a file.
     */
    public static void dictionaryExportToFile(Dictionary dictionary) {
        try {
            PrintWriter output = new PrintWriter(FILE_PATH);
            for (Word i : dictionary.searchAll()) {
                System.out.println(i.getWordTarget() + "\t" + i.getWordExplain() + "\n");
                output.write(i.getWordTarget() + "\t" + i.getWordExplain() + "\n");
            }
            output.close();
        } catch (FileNotFoundException e) {
            System.out.println("No file found");
        }
    }
}
