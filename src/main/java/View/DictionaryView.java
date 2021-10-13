package View;

import control.DictionaryCommandLine;
import model.Dictionary;
import model.Word;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DictionaryView {

    private JFrame main_frame;
    private Container action_container;
    private JTextField word_search_field;
    private JButton word_search_button;
    private JTextArea word_explain_area;
    private JList<Word> word_target_list;
    private JLabel word_target_list_label;
    private JLabel word_explain_label;
    private JComboBox<String> settings;
    DefaultListModel<Word> word_list_model = new DefaultListModel<>();
    ListSelectionModel selectionModel;

    public DictionaryView() {
        //initial
        DictionaryCommandLine command = new DictionaryCommandLine();
        Dictionary dictionary = new Dictionary();
        command.dictionaryAdvanced("insert from file", dictionary);
        command.dictionaryAdvanced("show all word", dictionary);


        /**
         *build a frame GUI
         */
        {
            main_frame = new JFrame("dictionary");
            main_frame.setBounds(450, 90, 450, 600);
            main_frame.setLayout(null);
            main_frame.setDefaultCloseOperation(main_frame.EXIT_ON_CLOSE);

            action_container = main_frame.getContentPane();
            action_container.setLayout(null);
        }

        /**
         * search field & button.
         */
        {
            word_search_field = new JTextField();
            word_search_field.setSize(225, 25);
            word_search_field.setLocation(0, 2);
            action_container.add(word_search_field);

            word_search_button = new JButton("search");
            word_search_button.setSize(100, 25);
            word_search_button.setLocation(225, 2);
            word_search_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String word_key = word_search_field.getText();
                }
            });
            action_container.add(word_search_button);
        }

        /**
         * settings: add word, remove word, fix word
         */
        {
            settings = new JComboBox<>(new String[]{"settings", "add word", "fix word", "remove word"});
            settings.setBounds(330, 2, 95, 25);
            settings.setSelectedItem(settings);
            settings.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String item = e.getItem().toString();
                        String word;
                        switch (item) {
                            case "add word":
                                command.dictionaryAdvanced("add word", dictionary);
                                updateWordList(dictionary);
                                break;
                            case "remove word":
                                command.dictionaryAdvanced("remove word", dictionary);
                                updateWordList(dictionary);
                                break;
                            case "fix word":
                                command.dictionaryAdvanced("fix word", dictionary);
                                updateWordList(dictionary);
                                break;
                            case "settings":
                                command.dictionaryAdvanced("show all word",dictionary);
                                break;
                            default: break;
                        }
                    }

                }
            });
            action_container.add(settings);
        }

        /**
         * labels
         */
        {
            word_target_list_label = new JLabel("Từ vựng");
            word_target_list_label.setBounds(0, 30, 225, 30);
            word_target_list_label.setFont(new Font("Arial", Font.BOLD, 25));
            action_container.add(word_target_list_label);

            word_explain_label = new JLabel("Y nghĩa");
            word_explain_label.setBounds(225, 30, 225, 30);
            word_explain_label.setFont(new Font("Arial", Font.BOLD, 25));
            action_container.add(word_explain_label);
        }

        /**
         * word target list
         */
        {
            word_target_list = new JList<>();
            word_target_list.setBounds(0, 60, 220, 540);
            word_target_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            word_target_list.setLayoutOrientation(JList.VERTICAL);
            word_target_list.setVisibleRowCount(-1);
            updateWordList(dictionary);
            selectionModel = this.word_target_list.getSelectionModel();
            selectionModel.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting()) {
                        updateWordExplain();
                    }
                }
            });
            action_container.add(word_target_list);
        }

        /**
         * word explain area
         */
        {
            word_explain_area = new JTextArea();
            word_explain_area.setBounds(230, 60, 220, 540);
            action_container.add(word_explain_area);
        }

        main_frame.setVisible(true);

    }

    /**
     * Update the display word list when you search for new words.
     *
     * @param dictionary dictionary to display
     */
    public void updateWordList(Dictionary dictionary) {
        this.word_list_model.removeAllElements();
        this.word_list_model.addAll(dictionary.getWord_list());
        this.word_target_list.setModel(this.word_list_model);
    }

    /**
     *
     */
    private void updateWordExplain() {
        int index = this.word_target_list.getSelectedIndex();
        if(index >= 0) {
            String new_word = this.word_list_model.getElementAt(index).getWord_target();
            String new_explain = this.word_list_model.getElementAt(index).getWord_explain();
            word_explain_area.setText(new_explain);
        }
    }
}

