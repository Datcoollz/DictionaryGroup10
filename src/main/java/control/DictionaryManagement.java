package control;

import model.Dictionary;
import model.Word;

import javax.swing.*;
import java.io.*;
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
                    dictionary.getWordList().add(new Word(ss[0], ss[1]));
                }
            }
            dictionary.getWordList().sort(
                    ((o1, o2) -> o1.getWordTarget().compareToIgnoreCase(o2.getWordTarget())));
        } catch (FileNotFoundException e) {
            System.out.println(("No file found"));
        }
    }

    /**
     * Search whether a word is in a dictionary.
     *
     * @return the index of the word in the dictionary,
     * the closest one to the left if it doesn't exist,
     * -1 otherwise
     */
    public static int dictionaryLookup(Dictionary dictionary, Word word) {
        if (!dictionary.getWordList().isEmpty()) {
            int index = Collections.binarySearch(dictionary.getWordList(), word,
                    (o1, o2) -> o1.getWordTarget().compareToIgnoreCase(o2.getWordTarget()));
            if (index < 0) {
                index = (index * -1) - 1;
            }
            if (index < dictionary.getWordList().size()) {
                return index;
            }
        }
        return -1;
    }


    /**
     * Add a new word to a dictionary.
     */
    public static void addWord(Dictionary dictionary, Word word) {
        int index = dictionaryLookup(dictionary, word);
        //Check if dictionary is empty or is at the end of dictionary
        if (dictionary.getWordList().isEmpty() || index >= dictionary.getWordList().size()) {
            dictionary.getWordList().add(word);
            JOptionPane.showMessageDialog(null, "Thêm thành công.");
        } else {
            //Check if word already exists in dictionary
            if (word.getWordTarget().equals(dictionary.getWordList().get(index).getWordTarget())) {
                JOptionPane.showMessageDialog(null, "Từ đã tồn tại.");
            } else {
                dictionary.getWordList().add(index, word);
                JOptionPane.showMessageDialog(null, "Thêm thành công.");
            }
        }
    }

    /**
     * Remove a word from a dictionary.
     */
    public static void removeWord(Dictionary dictionary, Word word) {
        int index = dictionaryLookup(dictionary, word);
        //Check if dictionary is empty
        if (dictionary.getWordList().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Từ điển trống.");
        } else {
            //check if word doesn't exist
            if (index == -1
                    || !word.getWordTarget().equals(dictionary.getWordList().get(index).getWordTarget())) {
                JOptionPane.showMessageDialog(null, "Từ không tồn tại");
            } else {
                dictionary.getWordList().remove(index);
                JOptionPane.showMessageDialog(null, "Xóa thành công.");
            }
        }
    }

    /**
     * change a word's definition.
     */
    public static void fixWord(Dictionary dictionary, Word word) {
        int index = dictionaryLookup(dictionary, word);
        //Check if dictionary is empty
        if (dictionary.getWordList().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Từ điển trống.");
        } else {
            //check if word doesn't exist
            if (index == -1
                    || !word.getWordTarget().equals(dictionary.getWordList().get(index).getWordTarget())) {
                JOptionPane.showMessageDialog(null, "Từ không tồn tại");
            } else {
                dictionary.getWordList().get(index).setWordExplain(word.getWordExplain());
                JOptionPane.showMessageDialog(null, "Sửa thành công");
            }
        }
    }

    /**
     * Output dictionary to a file.
     */
    public static void dictionaryExportToFile(Dictionary dictionary) {
        try {
            PrintWriter output = new PrintWriter(FILE_PATH);
            for (Word i : dictionary.getWordList()) {
                System.out.println(i.getWordTarget() + "\t" + i.getWordExplain() + "\n");
                output.write(i.getWordTarget() + "\t" + i.getWordExplain() + "\n");
            }
            output.close();
        } catch (FileNotFoundException e) {
            System.out.println("No file found");
        }
    }
}
