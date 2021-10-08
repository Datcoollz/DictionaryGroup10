import java.util.Scanner;

public class DictionaryCommandLine extends DictionaryManagement{
    private static final int SHOW_ALL_WORD = 914;
    private static final int INSERT_FROM_COMMANDLINE = 627;

    /**
     * Show all words in the dictionary
     */
    public void showAllWords() {
        if(this.word_list.isEmpty()) {
            System.out.println("Khong co tu trong tu dien.");
            return;
        }

        int count = 1;
        System.out.println("No|English|Vietnamese");
        for(Word i : this.word_list) {
            System.out.printf("%d|%s|%s\n", count, i.getWord_target(), i.getWord_explain());
            ++count;
        }
    }

    /**
     * Run basic commands.
     * Includes showAllWords() and InsertFromCommandLine();
     * @param COMMAND the command code
     */
    public void dictionaryBasic(int COMMAND) {
        switch (COMMAND) {
            case SHOW_ALL_WORD -> this.showAllWords();
            case INSERT_FROM_COMMANDLINE -> this.setInsertFromCommandline();
            default -> System.out.println("Lenh khong hop le");
        }
    }

    public static void main(String[] args) {
        System.out.println("Main is running");
    }
}
