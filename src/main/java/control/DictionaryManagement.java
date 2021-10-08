package control;

import model.Dictionary;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class DictionaryManagement {

    /**
     * Insert word from commandline
     */
    public static void setInsertFromCommandline(Dictionary dictionary) {
        Scanner input = new Scanner(System.in);
        int word_num = input.nextInt();
        String input_word;
        String input_definition;
        for (int i = 0; i < word_num; i++) {
            input_word = input.next();
            input_definition = input.nextLine();
            dictionary.addWord(input_word, input_definition);
        }
        input.close();
    }

    public void insertFromFile() {
        try {
            File file = new File("document.txt");
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String data = input.nextLine();

            }

        } catch (FileNotFoundException e) {
            System.out.println(("No file found"));
        }
    }


    public String dictionaryLookup() {
        return "";
    }

}
