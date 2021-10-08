import java.util.ArrayList;
import java.util.Objects;

/**
 * Dictionary class, stores all words.
 */
public class Dictionary {
    ArrayList<Word> word_list = new ArrayList<>();

    /**
     * Find the definition of a word in the list.
     *
     * @return the definition of the word
     */
    public String getDefinitionFromWord(String word_target) {
        for (Word i : word_list) {
            if(Objects.equals(i.getWord_target(), word_target)) {
                return i.getWord_explain();
            }
        }
        System.out.println("Word not found.");
        return "";
    }

    /**
     * Add a new word to the list
     * @param word_target added word
     * @param word_explain word's definition
     */
    public void addWord(String word_target, String word_explain) {
        this.word_list.add(new Word(word_target, word_explain));
    }
}
