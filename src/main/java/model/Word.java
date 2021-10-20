package model;

/**
 * Word class, contain the details of the word
 */
public class Word {


    //Old code
    private String wordTarget;
    private String wordExplain;

    public String getWordTarget() {
        return wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public Word() {
    }

    public Word(String wordTarget) {
        this.wordTarget = wordTarget;
        this.wordExplain = null;
    }

    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    @Override
    public String toString() {
        return this.wordTarget;
    }
}
