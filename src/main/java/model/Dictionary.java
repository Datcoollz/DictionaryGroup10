package model;

import java.util.ArrayList;

/**
 * Dictionary class, stores all words.
 */
public class Dictionary {
    private final ArrayList<Word> wordList = new ArrayList<>();

    public ArrayList<Word> getWordList() {
        return wordList;
    }
}
