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
}
