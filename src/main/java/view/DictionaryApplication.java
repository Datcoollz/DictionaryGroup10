package view;

import model.Dictionary;
import model.Word;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class DictionaryApplication {
    JFrame frame = new JFrame();
    JButton search_button = new JButton("Search");

    JList<Word> word_list = new JList<>();
    DefaultListModel<Word> word_list_model = new DefaultListModel<>();
    ListSelectionModel selectionModel;

    JTextArea word_explain = new JTextArea();

    /**
     * Update the display word list when you search for new words.
     *
     * @param dictionary dictionary to display
     */
    public void updateWordList(Dictionary dictionary) {
        this.word_list_model.removeAllElements();
        this.word_list_model.addAll(dictionary.getWord_list());
        this.word_list.setModel(this.word_list_model);
    }

    /**
     *
     */
    private void updateWordExplain() {
        int index = this.word_list.getSelectedIndex();
        if(index >= 0) {
            String new_word = this.word_list_model.getElementAt(index).getWord_target();
            String new_explain = this.word_list_model.getElementAt(index).getWord_explain();
            //this.word_explain.setText(new_explain);
            System.out.println("You chose the word: " + new_word + " ,meaning: " + new_explain);
        }
    }

    /**
     * Constructor.
     */
    public DictionaryApplication() {
        //Search button
//        this.search_button.setBounds(100, 100, 100, 40);
//        this.frame.add(this.search_button);

        //List of found words
        this.word_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.word_list.setLayoutOrientation(JList.VERTICAL);
        this.word_list.setVisibleRowCount(-1);
        this.selectionModel = this.word_list.getSelectionModel();
        this.selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    updateWordExplain();
                }
            }
        });
        this.frame.add(this.word_list);

        //Word explain area
        this.word_explain.setBounds(100, 100, 100, 40);
        this.frame.add(this.word_explain);
        

        //Final constructor
        this.frame.setSize(400, 500);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.frame.pack();
        this.frame.setVisible(true);
    }
}
