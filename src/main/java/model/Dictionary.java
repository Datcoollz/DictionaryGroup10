package model;


import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
        SortedMap<Character, Node> children = new TreeMap<>();
        Word wordData = null;

        //Constructor
        public Node(char ch) {
            this.ch = ch;
        }

        //Add child to node
        public void addChild(Node node, char c) {
            children.put(c, node);
        }
    }
    //Inserting method

    /**
     * Insert a new word into the list
     *
     * @param word the word to add
     */
    public void insert(Word word) {
        String key = word.getWordTarget();
        if (key == null) {
            return;
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
                return;
            }
            node.isWordEnding = true;
            node.wordData = word;
        }
    }
    //Searching methods

    /**
     * Search method but with word input
     */
    public Word search(Word word) {
        return search(word.getWordTarget());
    }

    /**
     * Search for a specific word
     *
     * @param key input word
     * @return the Word value of the word
     */
    public Word search(String key) {
        if (key == null) {
            return null;
        }
        Node node = root;
        key = key.toLowerCase();
        for (char ch : key.toCharArray()) {
            Node nextNode = node.children.get(ch);
            if (nextNode == null) {
                return null;
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
        return null;
    }

    /**
     * Get all the Word of all children node and the node itself
     *
     * @param node input node
     * @return ArrayList of Word
     */
    private ArrayList<Word> getAllChild(Node node) {
        ArrayList<Word> list = new ArrayList<>();
        if (node != null) {
            if (node.isWordEnding) {
                list.add(node.wordData);
            }
            for (Map.Entry<Character, Node> currentNode : node.children.entrySet()) {
                list.addAll(getAllChild(currentNode.getValue()));
            }
        }
        return list;
    }

    /**
     * Get all strings in the dictionary
     * Basically search for strings with an empty prefix
     */
    public ArrayList<Word> searchAll() {
        return searchPrefix("");
    }

    /**
     * Search prefix but with word as input.
     *
     * @param word input word
     * @return ArrayList of Word
     */
    public ArrayList<Word> searchPrefix(Word word) {
        return searchPrefix(word.getWordTarget());
    }

    /**
     * Get all string with input prefix
     *
     * @param key input prefix
     * @return ArrayList of Word
     */
    public ArrayList<Word> searchPrefix(String key) {
        Node node = root;
        key = key.toLowerCase();
        if (!key.equals("")) {
            for (char ch : key.toCharArray()) {
                Node nextNode = node.children.get(ch);
                //No word contains prefix
                if (nextNode == null) {
                    return null;
                }
                //Go to new word
                node = nextNode;
            }
        }
        return getAllChild(node);
    }
    //Deleting methods

    /**
     * Delete a word from the trie.
     *
     * @param key word to delete
     */
    public void delete(String key) {
        delete(root, key.toLowerCase(), 0);
    }

    /**
     * Delete method but with word input.
     */
    public void delete(Word word) {
        delete(word.getWordTarget());
    }

    /**
     * Recurring method for deleting nodes.
     *
     * @param currentNode current node
     * @param key         the word to delete
     * @param index       current index
     * @return true if the current node can be deleted,
     * false if not
     */
    private boolean delete(Node currentNode, String key, int index) {
        if (index == key.length()) {
            if (!currentNode.isWordEnding) {
                return false;
            } else {
                currentNode.isWordEnding = false;
                currentNode.wordData = null;
                return currentNode.children.isEmpty();
            }
        }

        char ch = key.charAt(index);
        Node nextNode = currentNode.children.get(ch);

        if (nextNode == null) {
            return false;
        }

        boolean shouldDeleteCurrentNode = delete(nextNode, key, index + 1) && !nextNode.isWordEnding;
        if (shouldDeleteCurrentNode) {
            currentNode.children.remove(ch);
            return currentNode.children.isEmpty();
        }
        return false;
    }
    //Fixing method

    /**
     * Edit a node Word value.
     *
     * @param word input word
     */
    public void editWord(Word word) {
        if (word.getWordTarget() == null) {
            return;
        }
        String key = word.getWordTarget().toLowerCase();
        Node node = root;
        for (char ch : key.toCharArray()) {
            Node nextNode = node.children.get(ch);
            if (nextNode == null) {
                return;
            }
            node = nextNode;
        }
        if (node != root) {
            if (node.isWordEnding) {
                node.wordData = word;
            }
        }
    }
}