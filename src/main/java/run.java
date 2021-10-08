import control.DictionaryManagement;
import control.DictionaryCommandLine;
import model.Word;
import model.Dictionary;
import java.util.Scanner;

public class run {

    public static void main(String[] args) {
        DictionaryCommandLine command = new DictionaryCommandLine();
        Dictionary dictionary = new Dictionary();
        command.dictionaryAdvanced("insert from file", dictionary);
        command.dictionaryAdvanced("show all word", dictionary);
        System.out.printf("available command:\n"
                + "-insert from file"
                + "-insert from commandline"
                + "-show all word"
                + "-look up"
                + "close (To quit using dictionary)");
        System.out.println("enter command:");
        String input_order;
        Scanner scanner = new Scanner(System.in);
        do {
            input_order = scanner.nextLine();
            command.dictionaryBasic(input_order, dictionary);

        }while(!input_order.equals("close"));
    }
}
