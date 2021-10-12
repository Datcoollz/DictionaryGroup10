package model;

import java.util.ArrayList;
import java.util.Objects;

import model.Word;
/**
 * Dictionary class, stores all words.
 */
public class Dictionary {
    private ArrayList<Word> word_list = new ArrayList<>();

    public ArrayList<Word> getWord_list() {
        return word_list;
    }

    /**
     * Add a new word to the list
     * @param word_target added word
     * @param word_explain word's definition
     */
    public void addWord(String word_target, String word_explain) {
        this.word_list.add(new Word(word_target, word_explain));
    }

    public void addWord(Word word) {
        this.word_list.add(word);
    }
}
