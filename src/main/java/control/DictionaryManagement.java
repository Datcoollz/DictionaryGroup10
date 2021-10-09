package control;

import model.Dictionary;
import model.Word;

import java.io.File;
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
     * @return tra ve nghia cua tu
     */
    public static void dictionaryLookup(Dictionary dictionary) {
        Scanner input = new Scanner(System.in);
        System.out.println("tim tu:");
        String word_target = input.nextLine();
        for (Word i : dictionary.getWord_list()) {
            if (Objects.equals(i.getWord_target(), word_target)) {
                System.out.println("nghia cua"
                        + word_target + " la: "
                        + i.getWord_explain());
            }
        }
        System.out.println("Word not found.");
    }


    /**
     * Add a new word to the list
     */
    public static void addWord(Dictionary dictionary) {
        Scanner input = new Scanner(System.in);
        System.out.print("them tu:");
        String word_target = input.nextLine();
        System.out.print("nghia cua tu");
        String word_explain = input.nextLine();
        dictionary.getWord_list().add(new Word(word_target, word_explain));
    }

    /**
     * remove a word.
     */
    public static void removeWord(Dictionary dictionary) {
        Scanner input = new Scanner(System.in);
        System.out.print("xoa tu:");
        String word_target = input.nextLine();
        dictionary.getWord_list().removeIf(i -> Objects.equals(i.getWord_target(), word_target));
    }

    /**
     * change a word's definition;
     * @param dictionary
     */
    public static void fixWord(Dictionary dictionary) {
        Scanner input = new Scanner(System.in);
        System.out.print("sua tu:");
        String word_target = input.nextLine();
        System.out.print("nghia moi:");
        String new_word_explain = input.nextLine();
        for (Word i : dictionary.getWord_list()) {
            if (Objects.equals(i.getWord_target(), word_target)) {
                i.setWord_explain(new_word_explain);
            }
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
