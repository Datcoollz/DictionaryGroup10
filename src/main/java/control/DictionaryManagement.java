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
            dictionary.getWord_list().add(new Word(input_word, input_definition));
        }
        input.close();
    }

    public static void insertFromFile(Dictionary dictionary) {
        try {
            File file = new File("dictionaries.txt");
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String data = input.nextLine();
                String[] ss = data.split("\t");
                dictionary.getWord_list().add(new Word(ss[0], ss[1]));
            }

        } catch (FileNotFoundException e) {
            System.out.println(("No file found"));
        }
    }

    /**
     * tim kiem nghia cua tu.
     *
     * @return tra ve nghia cua tu
     */
    public static String dictionaryLookup(Dictionary dictionary, String word_target) {
        for (Word i : dictionary.getWord_list()) {
            if (Objects.equals(i.getWord_target(), word_target)) {
                return i.getWord_explain();
            }
        }
        JOptionPane.showMessageDialog(null,"Word not found.");
        return "";
    }


    /**
     * Add a new word to the list
     */
    public static void addWord(Dictionary dictionary, Word word) {
        int index = Collections.binarySearch(dictionary.getWord_list(), word,
                (o1, o2) -> o1.getWord_target().compareToIgnoreCase(o2.getWord_target()));
        if (index < 0) {
            index = (index * -1) - 1;
        }
        if(word.getWord_target().equals(dictionary.getWord_list().get(index).getWord_target())) {
            JOptionPane.showMessageDialog(null, "tu khong ton tai");
        } else {
            dictionary.getWord_list().add(index, word);
            JOptionPane.showMessageDialog(null, "da sua tu");
        }
    }
    /**
     * remove a word.
     */
    public static void removeWord(Dictionary dictionary, String word_target) {
        dictionary.getWord_list().removeIf(i -> Objects.equals(i.getWord_target(), word_target));
    }

    /**
     * change a word's definition;
     *
     * @param dictionary
     */
    public static void fixWord(Dictionary dictionary, Word word) {
        int index = Collections.binarySearch(dictionary.getWord_list(), word,
                (o1, o2) -> o1.getWord_target().compareToIgnoreCase(o2.getWord_target()));
        if (index < 0) {
            index = (index * -1) - 1;
        }
        if(!word.getWord_target().equals(dictionary.getWord_list().get(index).getWord_target())) {
            JOptionPane.showMessageDialog(null, "tu khong ton tai");
        } else {
            dictionary.getWord_list().get(index).setWord_explain(word.getWord_explain());
            JOptionPane.showMessageDialog(null, "da sua tu");
        }
    }

    /**
     * xuat du lieu ra file.
     */
    public static void dictionaryExportTofile(Dictionary dictionary) {
        File file = new File("dictionaries.txt");
        try {
            PrintWriter output = new PrintWriter(file);
            for (Word i : dictionary.getWord_list()) {
                output.println(i.getWord_target() + "\t" + i.getWord_explain());
            }
            output.close();
        } catch (FileNotFoundException e) {
            System.out.println("No file found");
        }
    }

}
