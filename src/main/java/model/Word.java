package model;

/**
 * model.Word class, contains a word and its definition.
 */
public class Word {
    private final String wordTarget;
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

    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    @Override
    public String toString() {
        return this.wordTarget;
    }
}
