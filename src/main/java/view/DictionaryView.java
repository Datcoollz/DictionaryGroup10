package view;

import control.DictionaryCommandLine;
import model.Dictionary;
import model.Word;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DictionaryView {
    private JPanel mainPanel;
    private JPanel main;
    private JPanel menu;
    private JButton API;
    private JButton search;
    private JPanel googleAPI;
    private JSplitPane mainDictionary;
    private JTextField wordSearchField;
    private JButton wordSearchButton;
    private JButton addButton;
    private JEditorPane wordExplainArea;
    private JScrollPane searchScrollPane;
    private JList<Word> wordTargetList;
    private JPanel searchSide;
    private JPanel explainSide;
    private JScrollPane explainScrollPane;
    DefaultListModel<Word> wordListModel = new DefaultListModel<>();
    ListSelectionModel selectionModel;
    private final CardLayout card = (CardLayout) main.getLayout();

    public DictionaryView(Dictionary dictionary) {
        JFrame frame = new JFrame("Dictionary");
        frame.setContentPane(mainPanel);
        frame.setBounds(400, 30, 800, 500);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //menu
        search.addActionListener(e -> card.show(main, "mainDictionary"));
        API.addActionListener(e -> card.show(main, "googleAPI"));

        //search field & button.
        {
            //Search button-> get from wordSearchField
            wordSearchButton.addActionListener(e -> {
                String wordKey = wordSearchField.getText();
                Dictionary searchDictionary = DictionaryCommandLine.dictionaryAdvanced(
                        DictionaryCommandLine.SEARCH_KEY_WORD, dictionary,
                        new Word(wordKey, ""));
                assert searchDictionary != null;
                updateWordList(searchDictionary);
            });
        }

        //wordTarget list
        {
            wordTargetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            wordTargetList.setVisibleRowCount(-1);
            updateWordList(dictionary);
            selectionModel = this.wordTargetList.getSelectionModel();
            selectionModel.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    updateWordExplain();
                }
            });
        }

        //add buttons
        addButton.addActionListener(e -> {
            //implement later
        });

        //word explain area
        {
            wordExplainArea.setContentType("text/html");
            explainScrollPane.setViewportView(wordExplainArea);
        }

    }
    /**
     * Update wordTargetList when you search for new words.
     *
     * @param dictionary dictionary to display
     */
    public void updateWordList(Dictionary dictionary) {
        this.wordListModel.removeAllElements();
        this.wordListModel.addAll(dictionary.getWordList());
        this.wordTargetList.setModel(this.wordListModel);
    }

    /**
     * Update wordExplainArea when you pick a word.
     */
    private void updateWordExplain() {
        int index = this.wordTargetList.getSelectedIndex();
        if (index >= 0) {
            //String newWord = this.wordListModel.getElementAt(index).getWordTarget();
            String newExplain = "<html>" + this.wordListModel.getElementAt(index).getWordExplain() + "</html";
            wordExplainArea.setText(newExplain);
        }
    }

}
