package control;

import model.Dictionary;
import model.Word;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;
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
            input_word = input.next();
            input_definition = input.nextLine();
            dictionary.addWord(input_word, input_definition);
        }
        input.close();
    }

    public static void insertFromFile(Dictionary dictionary) {
        try {
            File file = new File("document.txt");
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


    public static String dictionaryLookup(Dictionary dictionary, String word_target) {
        for (Word i : dictionary.getWord_list()) {
            if (Objects.equals(i.getWord_target(), word_target)) {
                return i.getWord_explain();
            }
        }
        System.out.println("Word not found.");
        return "";
    }

}
