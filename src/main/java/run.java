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
        System.out.println("available command:\n"
                + "-insert from file\n"
                + "-show all word\n"
                + "-look up\n"
                + "-add word\n"
                + "-remove word\n"
                + "-fix word\n"
                + "-export to file\n"
                + "close (To quit using dictionary)");
        String input_order;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("enter command:");
            input_order = scanner.nextLine();
            command.dictionaryAdvanced(input_order, dictionary);
        }while(!input_order.equals("close"));
        scanner.close();
    }
}
