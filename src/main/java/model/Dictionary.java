package model;

import java.util.ArrayList;
import java.util.Objects;

import model.Word;

/**
 * Dictionary class, stores all words.
 */
public class Dictionary {
    private final ArrayList<Word> wordList = new ArrayList<>();

    public ArrayList<Word> getWordList() {
        return wordList;
    }

    /**
     * Add a new word to the list
     *
     * @param wordTarget  added word
     * @param wordExplain word's definition
     */
    public void addWord(String wordTarget, String wordExplain) {
        this.wordList.add(new Word(wordTarget, wordExplain));
    }
}
