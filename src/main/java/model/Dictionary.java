package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Dictionary class, stores all words.
 * Uses a trie data structure.
 */
public class Dictionary {
    private final char rootCharacter = '\0';
    private final Node root = new Node(rootCharacter);

    private static class Node {
        char ch;
        boolean isWordEnding = false;
        HashMap<Character, Node> children = new HashMap<>();
        Word wordData;

        //Constructor
        public Node(char ch) {
            this.ch = ch;
        }

        //Add child to node
        public void addChild(Node node, char c) {
            children.put(c, node);
        }
    }

    /**
     * Insert a new word into the list
     * @param word the word to add
     * @return true if the word is added,
     * false if the word is empty or already in the list
     */
    public boolean insert(Word word) {
        String key = word.getWordTarget();
        if (key == null) {
            return false;
        }
        //Change the key to all lowercase
        key = key.toLowerCase();
        Node node = root;

        for (char ch : key.toCharArray()) {
            Node nextNode = node.children.get(ch);
            //If next character doesn't exist
            if (nextNode == null) {
                nextNode = new Node(ch);
                node.addChild(nextNode, ch);
            }
            node = nextNode;
        }
        //Check if still at root
        //Check if word already in the list
        if (node != root) {
            if (node.isWordEnding) {
                return false;
            }
            node.isWordEnding = true;
            node.wordData = word;
        }
        return true;
    }

    /**
     * Search for a specific word
     * @param key input word
     * @return the Word value of the word
     */
    public Word search(String key) {
        if (key == null) {
            return new Word();
        }
        Node node = root;
        key = key.toLowerCase();
        for (char ch : key.toCharArray()) {
            Node nextNode = node.children.get(ch);
            if (nextNode == null) {
                return new Word();
            }
            node = nextNode;
        }
        //Check if word is there
        if (node != root) {
            if (node.isWordEnding) {
                return node.wordData;
            }
        }
        //Else return empty word
        return new Word();
    }

    /**
     * Get all the child of a node and the node word itself
     * @param node input node
     * @return ArrayList of Word
     */
    private ArrayList<Word> getAllChild(Node node) {
        ArrayList<Word> list = new ArrayList<>();
        if (node.isWordEnding) {
            list.add(node.wordData);
        }
        for (Map.Entry<Character, Node> currentNode : node.children.entrySet()) {
            list.addAll(getAllChild(currentNode.getValue()));
        }
        return list;
    }

    /**
     * Get all string with input prefix
     * @param key input prefix
     * @return ArrayList of strings
     */
    public ArrayList<Word> searchPrefix(String key) {
        Node node = root;
        key = key.toLowerCase();
        for (char ch : key.toCharArray()) {
            Node nextNode = node.children.get(ch);
            //No word contains prefix
            if (nextNode == null) {
                return null;
            }
            //Go to new word
            node = nextNode;
        }
        return getAllChild(node);
    }

    /**
     * Get all strings in the dictionary
     * Basically search for strings with an empty prefix
     */
    public ArrayList<Word> searchAll() {
        return searchPrefix("");
    }
}
